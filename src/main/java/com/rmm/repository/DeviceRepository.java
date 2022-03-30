package com.rmm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rmm.model.customer.Customer;
import com.rmm.model.device.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
	
	List<Device> findByCustomer(Customer customer);
} 
