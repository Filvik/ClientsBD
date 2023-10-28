package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Ответа на запрос")
public class ContactsAccount {

    @JsonProperty("phones")
    @Schema(description = "Коллекция телефонов клиента")
    private List<String> phones;

    @JsonProperty("emails")
    @Schema(description = "Коллекция телефонов клиента")
    private List<String> emails;

}
