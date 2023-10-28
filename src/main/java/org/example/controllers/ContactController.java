package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.BaseResponse;
import org.example.model.ContactsAccount;
import org.example.model.ContactsResponse;
import org.example.service.ContactService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ContactController {

    private static final Logger log = LogManager.getLogger(ContactController.class);
    private final ContactService contactService;

    @PostMapping(value = "/clients/{clientId}/contacts/")
    @Operation(summary = "Запрос на добавления нового контакта для клиента", tags = "Эндпоинт добавления нового контакта для клиента")
    public BaseResponse addNewContact(@PathVariable @Parameter(description = "Идентификатор клиента") long clientId,
            @RequestBody @Parameter(description = "Данные контакта") ContactsAccount contactsForAccount) {
        log.info("Вызван метод addNewContact");
        return contactService.addContacts(contactsForAccount, clientId);
    }

    @GetMapping(value = "/clients/{clientId}/contacts/")
    @Operation(summary = "Запрос всех контактов клиента", tags = "Эндпоинт возвращает все контакты клиента")
    public BaseResponse getCollection(@PathVariable @Parameter(description = "Идентификатор аккаунта") long clientId,
                                          @RequestParam(required = false) String typeContact) {
        return contactService.getCollection(clientId,typeContact);
    }
}
