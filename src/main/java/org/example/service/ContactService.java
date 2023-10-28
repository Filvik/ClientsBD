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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService {
    private static final Logger log = LogManager.getLogger(ClientService.class);
    private static final String TYPE_PHONES = "phones";
    private static final String TYPE_EMAILS = "emails";
    private final ClientRepository clientRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;


    /**
     * Метод добавляет контакт(ы) для существующего аккаунта.
     *
     * @param contactsAccount Данные для добавления.
     * @return Объект класса BaseResponse.
     */
    public BaseResponse addContacts(ContactsAccount contactsAccount, long clientId) {

        Optional<ClientEntity> account = clientRepository.findById(clientId);
        int sizePhoneRepositoryBefore = phoneRepository.findAll().size();
        int sizeEmailRepositoryBefore = emailRepository.findAll().size();

        try {
            if (account.isEmpty()) {
                return new BaseResponse(StatusEnum.ERROR, "Аккаунта с таким ID не существует");
            }
            if ((contactsAccount.getPhones() == null || contactsAccount.getPhones().isEmpty()) &&
                    (contactsAccount.getEmails() == null || contactsAccount.getEmails().isEmpty())) {
                return new BaseResponse(StatusEnum.ERROR, "Отсутствуют контакты для добавления");
            }
            if (contactsAccount.getPhones() != null && !contactsAccount.getPhones().isEmpty()) {
                contactsAccount.getPhones().stream()
                        .filter(s -> !s.isEmpty())
                        .forEach(ph -> {
                            ContactPhoneEntity phone = new ContactPhoneEntity();
                            phone.setPhoneNumber(ph);
                            phone.setAccount(account.get());
                            try {
                                phoneRepository.saveAndFlush(phone);
                            } catch (DataIntegrityViolationException exception) {
                                log.error("Попытка добавить уже существующий телефон в перечне добавляемых элементов коллекции контактов: "
                                        + exception + " для пользователя с id: " + clientId);
                            }
                        });
            }
            String desc = "";
            if (contactsAccount.getEmails() != null && !contactsAccount.getEmails().isEmpty()) {
                contactsAccount.getEmails().stream()
                        .filter(s -> !s.isEmpty())
                        .forEach(em -> {
                            ContactEmailEntity email = new ContactEmailEntity();
                            email.setAccount(account.get());
                            email.setEmail(em);
                            try {
                                emailRepository.saveAndFlush(email);
                            } catch (DataIntegrityViolationException exception) {
                                log.error("Попытка добавить уже существующий email в перечне добавляемых элементов коллекции контактов: "
                                        + exception + " для пользователя с id: " + clientId);
                            }
                        });
                int sizePhoneRepositoryAfter = phoneRepository.findAll().size();
                if (sizePhoneRepositoryAfter - sizePhoneRepositoryBefore > 0) {
                    desc = "Для аккаунта с id: " + account.get().getId() + " добавлен телефон(ы). ";
                } else {
                    desc = "Отсутствуют телефон(ы) для добавления";
                }
                int sizeEmailRepositoryAfter = emailRepository.findAll().size();
                if (sizeEmailRepositoryAfter - sizeEmailRepositoryBefore > 0) {
                    desc = desc + " Для аккаунта с id: " + account.get().getId() + " добавлен email(ы).";
                } else {
                    desc = desc + " Отсутствуют email(ы) для добавления";
                }
            }
            return new BaseResponse(StatusEnum.OK, desc);
        } catch (Exception exception) {
            log.error("Ошибка добавления нового аккаунта: " + exception);
            return new BaseResponse(StatusEnum.ERROR, "Ошибка добавления нового аккаунта");
        }
    }


    /**
     * Метод формирует коллекцию контактов аккаунта.
     *
     * @param id   Id аккаунта.
     * @param type Тип контакта.
     * @return Объект класса ContactsResponse.
     */
    public BaseResponse getCollection(Long id, String type) {

        ContactsResponse contactsResponse = new ContactsResponse();
        Optional<ClientEntity> entity = clientRepository.findById(id);

        if (entity.isPresent()) {
            if (type != null) {
                switch (type) {
                    case TYPE_PHONES ->
                            contactsResponse.setPhone(phoneRepository.findByAccount(entity.get()).stream()
                            .map(ContactPhoneEntity::getPhoneNumber)
                            .toList());
                    case TYPE_EMAILS ->
                            contactsResponse.setEmail(emailRepository.findByAccount(entity.get()).stream()
                            .map(ContactEmailEntity::getEmail)
                            .toList());
                    default -> {
                        return new BaseResponse(StatusEnum.ERROR, "Некорректно выбран тип контакта");
                    }
                }
            } else {
                contactsResponse.setEmail(emailRepository.findByAccount(entity.get()).stream().map(ContactEmailEntity::getEmail).toList());
                contactsResponse.setPhone(phoneRepository.findByAccount(entity.get()).stream().map(ContactPhoneEntity::getPhoneNumber).toList());
            }
        } else {
            return new BaseResponse(StatusEnum.ERROR, "Не существует такого аккаунта");
        }
        return new BaseResponse(null, contactsResponse);
    }

    /**
     * Метод формирует информацию по аккаунту.
     *
     * @param id Id аккаунта.
     * @return Объект класса ContactsAccountResponse.
     */
    public BaseResponse getInformation(long id) {

        ContactsAccountResponse response = new ContactsAccountResponse();
        Optional<ClientEntity> entity = clientRepository.findById(id);

        if (entity.isPresent()) {
            response.setAccount(entity.get());
            response.setEmails(emailRepository.findByAccount(entity.get()).stream().map(ContactEmailEntity::getEmail).toList());
            response.setPhones(phoneRepository.findByAccount(entity.get()).stream().map(ContactPhoneEntity::getPhoneNumber).toList());
        } else {
            return new BaseResponse(StatusEnum.ERROR, "Не существует такого аккаунта");
        }
        return new BaseResponse(null, response);
    }
}
