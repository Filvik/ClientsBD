package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Ответа на запрос контактов")
public class ContactsResponse {

    @JsonProperty("phones")
    @Schema(description = "Коллекция телефонов клиента")
    private List<String> phone;

    @JsonProperty("emails")
    @Schema(description = "Коллекция телефонов клиента")
    private List<String> email;

    @JsonProperty("description")
    @Schema(description = "Успех операции")
    private String description;
}
