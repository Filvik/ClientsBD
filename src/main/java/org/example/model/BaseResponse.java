package org.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Базовый ответ на запрос")
public class BaseResponse {

    @JsonProperty("status")
    @Schema(description = "Статус")
    private String status;

    @JsonProperty("description")
    @Schema(description = "Описание операции/ошибки")
    private String description;

    @JsonProperty("data")
    @Schema(description = "Значение")
    private Object data;


    public BaseResponse() {
    }

    public BaseResponse(String description, Object data){
        this.status = StatusEnum.OK.getText();
        this.description = description;
        this.data = data;
    }

    public BaseResponse(StatusEnum status, String description){
        this.status = status.getText();
        this.description = description;
    }
}
