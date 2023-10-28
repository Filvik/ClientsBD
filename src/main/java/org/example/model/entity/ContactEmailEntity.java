package org.example.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Schema(name = "Email контакт")
@Table()
public class ContactEmailEntity {

    @Id
    @Schema(description = "Идентификатор")
    private String email;

    @Schema(description = "account_id")
    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", foreignKey = @ForeignKey(name = "FK_1"), nullable = false)
    private ClientEntity account;

    public ContactEmailEntity() {
    }

    public ContactEmailEntity(ClientEntity account, String email) {
        this.account = account;
        this.email = email;
    }
}
