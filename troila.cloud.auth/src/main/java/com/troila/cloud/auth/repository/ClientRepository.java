package com.troila.cloud.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.troila.cloud.auth.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
	
	Optional<Client> findByClientId(String clientId);

}
