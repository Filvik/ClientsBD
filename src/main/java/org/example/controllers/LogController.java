package org.example.controllers;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.service.LogService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;
    private static final Logger log = LogManager.getLogger(LogController.class);

    @RequestMapping(value = {"/logs/"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    @Operation(summary = "Запрос логов", tags = "Эндпоинт получения лог-файла")
    public ResponseEntity<InputStreamResource> logs() {
        log.info("Запрос лог-файла");
        return logService.logs();
    }
}
