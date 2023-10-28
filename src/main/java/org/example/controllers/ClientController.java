package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.entity.ClientEntity;
import org.example.model.BaseResponse;
import org.example.service.ClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients/")
@RequiredArgsConstructor
public class ClientController {

    private static final Logger log = LogManager.getLogger(ClientController.class);
    private final ClientService clientService;

    @PostMapping
    @Operation(summary = "Создание нового клиента", tags = "Эндпоинт добавления нового клиента")
    public BaseResponse addClient(@RequestBody@Parameter(description = "Новый клиент") ClientEntity newAccount) {
        log.info("Добавление нового клиента с параметрами{}", newAccount);
        return clientService.addAccount(newAccount);
    }

    @GetMapping
    @Operation(summary = "Запрос всех существующих клиентов",
            tags = "Эндпоинт возвращает информацию о всех существующих клиентах")
    public BaseResponse getClient() {
        return clientService.getClientList();
    }

    @GetMapping(value = "{id}/")
    @Operation(summary = "Запрос информации клиента", tags = "Эндпоинт запрашивает информацию клиента")
    public BaseResponse getClientById(@PathVariable @Parameter(description = "Идентификатор аккаунта") long id) {
        log.info("Вызван метод getInformation");
        return clientService.getInformation(id);
    }
}
