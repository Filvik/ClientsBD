package org.example.repository;

import org.example.model.entity.ClientEntity;
import org.example.model.entity.ContactPhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRepository extends JpaRepository<ContactPhoneEntity, String> {
    List<ContactPhoneEntity> findByAccount(ClientEntity account);
}
