package com.rmm.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.rmm.model.*;
import com.rmm.repository.CustomerRepository;
import com.rmm.repository.DeviceRepository;
import com.rmm.repository.SystemServiceRepository;
import com.rmm.security.JwtUtil;

@RestController
@RequestMapping({ "/systemService" })
public class SystemServiceController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private SystemServiceRepository systemServiceRepository;

	@PostMapping(value = "/addService")
	public ResponseEntity create(@RequestHeader("Authorization") String authorization, @RequestBody SystemServiceRequest systemServiceRequest) {

		Optional<SystemService> systemService = systemServiceRepository.findByServiceName(systemServiceRequest.getServiceName());
		
		if(systemService.isEmpty())
		{
			return ResponseEntity.notFound().build();
		}
				
		Customer customer = returnCustomerFromToken(authorization);
		
		if(customer.getSystemServices() == null || customer.getSystemServices().size() == 0) {
						
			List<SystemService> list = new ArrayList<>();
			list.add(systemService.get());
			
			customer.setSystemServices(list);
			
			Customer updated = customerRepository.save(customer);
			
			return ResponseEntity.ok().body(updated);
		}
		
		if(customer.getSystemServices().stream().anyMatch(service -> service.getServiceName().equals(systemServiceRequest.getServiceName()))) {
			return ResponseEntity.badRequest().body("This service has been already selected");
		}
		
		customer.getSystemServices().add(systemService.get());
		
		Customer updated = customerRepository.save(customer);
		
		return ResponseEntity.ok().body(updated);
	}
	
	@GetMapping(path = { "/myServices" })
	public ResponseEntity findAllMyServices(@RequestHeader("Authorization") String authorization) {

		Customer customer = returnCustomerFromToken(authorization);		

		return ResponseEntity.ok().body(customer.getSystemServices());
	}
	
	@DeleteMapping(path = { "/myServices/{serviceName}" })
	public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorization, @PathVariable String serviceName) {

		Optional<SystemService> systemService = systemServiceRepository.findByServiceName(serviceName);
		
		if(systemService.isEmpty())
		{
			return ResponseEntity.notFound().build();
		}		
		
		Customer customer = returnCustomerFromToken(authorization);

		customerRepository.findByUserName(customer.getUserName()).get()
				.getSystemServices()
				.removeIf(recordSystemService -> recordSystemService.getServiceName().equals(systemService.get().getServiceName()));
		
		customerRepository.save(customer);
				
		return ResponseEntity.ok().build();		

	}
	
	@GetMapping(path = { "/myServices/cost" })
	public ResponseEntity calculateCost(@RequestHeader("Authorization") String authorization) {

		Customer customer = returnCustomerFromToken(authorization);
		
		List<Device> devices = deviceRepository.findByCustomer(customer);
		
		Map<String, Integer> hashMap = returnMapQuantityOperatingSystem(devices);
				
		Integer count = Math.multiplyExact(devices.size(), 4);
		
		for (SystemService systemService : customer.getSystemServices()) {
			for(String key: hashMap.keySet()) {
				count += Math.multiplyExact(systemService.getPricePerSystem().get(key), hashMap.get(key));
				
			}			
		}		    		

		return ResponseEntity.ok().body(count);
	}

	public Map<String, Integer> returnMapQuantityOperatingSystem(List<Device> devices) {
		
		int countWindows =0;
		int countMac =0;		
				
		for (Device device : devices) {
			if(device.getType().contains("Windows") || "Windows".equals(device.getType())) 
				countWindows += 1;
			else
				countMac += 1;				
		}
		
		Map<String, Integer> operatingSystems = new HashMap<String, Integer>();
		operatingSystems.put("Windows", countWindows);
		operatingSystems.put("Mac", countMac);
		
		return operatingSystems;
		
	}
	
	private Customer returnCustomerFromToken(String token) {
		String jwt = token.substring(7);
		String userName = jwtUtil.extractUserName(jwt);
		return customerRepository.findByUserName(userName)
				.orElseThrow(() -> new UsernameNotFoundException("Not found" + userName));

	}
}
