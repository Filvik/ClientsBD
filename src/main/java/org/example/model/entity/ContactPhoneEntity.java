package org.example.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table()
@Schema(name = "Контактный телефон")
public class ContactPhoneEntity {

    @Id
    @Schema(description = "Phone")
    private String phoneNumber;

    @Schema(description = "account_id")
    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", foreignKey = @ForeignKey(name = "FK_2"), nullable = false)
    private ClientEntity account;


    public ContactPhoneEntity() {
    }

    public ContactPhoneEntity(ClientEntity account, String phoneNumber) {
        this.account = account;
        this.phoneNumber = phoneNumber;
    }
}
