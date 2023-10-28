package org.example.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints =
  {@UniqueConstraint(name = "CLIENT", columnNames = {"FIRST_NAME", "LAST_NAME"})})
@Schema(name = "Сущность клиента")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор")
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    @JsonProperty("first_name")
    @Schema(description = "Имя")
    @NotBlank
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    @JsonProperty("last_name")
    @Schema(description = "Фамилия")
    @NotBlank
    private String lastName;

    public ClientEntity() {
    }
}
