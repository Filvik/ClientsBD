package org.example.repository;

import org.example.model.entity.ClientEntity;
import org.example.model.entity.ContactEmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRepository extends JpaRepository<ContactEmailEntity, String> {
    List<ContactEmailEntity> findByAccount(ClientEntity account);
}
