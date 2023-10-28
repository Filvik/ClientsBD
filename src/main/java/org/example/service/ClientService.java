package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.*;
import org.example.model.entity.ClientEntity;
import org.example.model.entity.ContactEmailEntity;
import org.example.model.entity.ContactPhoneEntity;
import org.example.repository.ClientRepository;
import org.example.repository.EmailRepository;
import org.example.repository.PhoneRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ClientService {

    private static final Logger log = LogManager.getLogger(ClientService.class);
    private final ClientRepository clientRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;

    /**
     * Метод создает нового клиента, если он не существует в БД.
     * Считаем что клиент уже существует в базе, если существует запись в базе с идентичными именем и фамилией
     *
     * @param account  Данные клиента.
     * @return Объект класса BaseResponse.
     */
    public BaseResponse addAccount(ClientEntity account) {

        BaseResponse response = new BaseResponse();

        try {
            clientRepository.saveAndFlush(account);
            response.setDescription("Аккаунт успешно добавлен");
        }
        catch (DataIntegrityViolationException exception){
            response.setDescription("Аккаунт данного клиента уже существует в БД");
            log.error("Аккаунт данного клиента уже существует в БД: " + exception);
        }
        catch (Exception exception) {
            response.setDescription("При добавление нового аккаунта произошла ошибка");
            log.error("При добавление нового аккаунта произошла ошибка: " + exception);
        }
        return response;
    }

    /**
     * Метод формирует коллекцию существующих клиентов.
     *
     * @return Объект класса CollectionAccountsResponse.
     */
    public BaseResponse getClientList() {
        return new BaseResponse(null, clientRepository.findAll());
    }

    /**
     * Метод формирует информацию по аккаунту.
     *
     * @param id Id аккаунта.
     * @return Объект класса ContactsAccountResponse.
     */
    public BaseResponse getInformation(long id) {
        AtomicReference<BaseResponse> baseResponse = new AtomicReference<>();
        ContactsAccountResponse response = new ContactsAccountResponse();
        clientRepository.findById(id).ifPresentOrElse(clientEntity -> {
            response.setEmails(emailRepository.findByAccount(clientEntity).stream()
                    .map(ContactEmailEntity::getEmail)
                    .toList());
            response.setPhones(phoneRepository.findByAccount(clientEntity).stream()
                    .map(ContactPhoneEntity::getPhoneNumber)
                    .toList());
            baseResponse.set(new BaseResponse(null, response));
        }, () -> {
            log.warn("Попытка запроса данных несуществующего клиента: {}", id);
            baseResponse.set(new BaseResponse(StatusEnum.ERROR, String.format("Клиента с id %s не существует", id)));
        });
        return baseResponse.get();
    }
}
