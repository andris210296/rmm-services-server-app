package com.rmm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rmm.model.SystemService;

@Repository
public interface SystemServiceRepository extends JpaRepository<SystemService, Long> {
	
	Optional<SystemService> findByServiceName(String serviceName);
	
} 
