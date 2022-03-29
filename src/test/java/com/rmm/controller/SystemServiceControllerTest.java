package com.rmm.controller;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.rmm.model.Customer;
import com.rmm.model.Device;
import com.rmm.model.SystemService;
import com.rmm.repository.CustomerRepository;
import com.rmm.security.JwtUtil;

@RunWith(MockitoJUnitRunner.class)
public class SystemServiceControllerTest {
	
	@Mock
	private JwtUtil jwtUtil;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private SystemServiceController systemServiceController;
	
	
	private static final String AUTHORIZATION = "systemServiceController";
	private static final String USER_NAME = "userName";
	private static final String PASSWORD = "userName";
	
	@BeforeEach
	private void init() {
		MockitoAnnotations.initMocks(this);
		when(jwtUtil.extractUserName(anyString())).thenReturn(USER_NAME);
		when(customerRepository.findByUserName(anyString())).thenReturn(generateOptionalCustomer());
		
	}
	
	@Test
	public void calculateCostTest() {
		
		systemServiceController.calculateCost(AUTHORIZATION);
		
		
		
	}
	
	@Test
	public void returnMapQuantityOperatingSystemTest() {
		Map<String, Integer> response = systemServiceController.returnMapQuantityOperatingSystem(generateListDevices());
		
		//assertEquals(1, response.get(""));

	}
	
	private Customer generateCustomer() {
		return 	Customer.builder()
				.id(1)
				.userName(USER_NAME)
				.password(PASSWORD)
				.devices(generateListDevices())
				.systemServices(generateListSystemService())
				.build();
	}
	
	private Optional<Customer> generateOptionalCustomer() {
		return Optional.of(
				Customer.builder()
				.id(1)
				.userName(USER_NAME)
				.password(PASSWORD)
				.devices(generateListDevices())
				.systemServices(generateListSystemService())
				.build());
	}
	
	private List<Device> generateListDevices(){
		List<Device> devices = new ArrayList<Device>();
		
		devices.add(
				Device.builder()
					.id(1)
					.systemName("MyComputer")
					.type("Windows Workstation")
					.build()
					);
		
		devices.add(
				Device.builder()
					.id(2)
					.systemName("MyMac")
					.type("Mac")
					.build()
					);		
		
		return devices;
	}
	
	private List<SystemService> generateListSystemService(){
		
		HashMap<String, Integer> difValues = new HashMap<String, Integer>();
		difValues.put("Windows", 5);
		difValues.put("Mac", 7);

		
		List<SystemService> systemServices = new ArrayList<SystemService>();
		
		systemServices.add(
				SystemService.builder()
					.id(1)
					.serviceName("Antivirus")
					.pricePerSystem(difValues)
				.build());
		
		
		
		HashMap<String, Integer> sameValues = new HashMap<String, Integer>();
		sameValues.put("Windows", 2);
		sameValues.put("Mac", 2);
		
		systemServices.add(
				SystemService.builder()
					.id(2)
					.serviceName("PSA")
					.pricePerSystem(sameValues)
				.build());
		
		
		return systemServices;
	}

}
