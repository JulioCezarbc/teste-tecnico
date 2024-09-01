package com.julio.tgid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientRepository, UUID> {
}
