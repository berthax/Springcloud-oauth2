package com.troila.cloud.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.troila.cloud.auth.model.AuUser;

public interface AuUserRepository extends JpaRepository<AuUser, Long>{
	Optional<AuUser> findByName(String name);
}
