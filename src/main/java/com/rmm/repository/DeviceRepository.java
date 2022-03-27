package com.rmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rmm.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
	
	
} 
