package com.rmm.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.rmm.model.customer.Customer;
import com.rmm.model.device.Device;
import com.rmm.model.systemservice.SystemService;
import com.rmm.model.systemservice.SystemServiceRequest;
import com.rmm.repository.CustomerRepository;
import com.rmm.repository.DeviceRepository;
import com.rmm.repository.SystemServiceRepository;
import com.rmm.security.JwtUtil;
import com.rmm.utils.RmmUtils;

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
	
	private static final String AUTHORIZATION = "Authorization";

	@PostMapping(value = "/addService")
	public ResponseEntity create(@RequestHeader(AUTHORIZATION) String authorization, @RequestBody SystemServiceRequest systemServiceRequest) {

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
			
			return ResponseEntity.ok().body(RmmUtils.customerToCustomerServiceResponse(updated));
		}
		
		if(customer.getSystemServices().stream().anyMatch(service -> service.getServiceName().equals(systemServiceRequest.getServiceName()))) {
			return ResponseEntity.badRequest().body(RmmUtils.createMessage("This service has been already selected"));
		}
		
		customer.getSystemServices().add(systemService.get());
		
		Customer updated = customerRepository.save(customer);
		
		return ResponseEntity.ok().body(RmmUtils.customerToCustomerServiceResponse(updated));
	}
	
	@GetMapping(path = { "/myServices" })
	public ResponseEntity findAllMyServices(@RequestHeader(AUTHORIZATION) String authorization) {

		Customer customer = returnCustomerFromToken(authorization);		

		return ResponseEntity.ok().body(RmmUtils.customerToCustomerServiceResponse(customer));
	}
	
	@DeleteMapping(path = { "/myServices/{serviceName}" })
	public ResponseEntity<?> delete(@RequestHeader(AUTHORIZATION) String authorization, @PathVariable String serviceName) {

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
	public ResponseEntity calculateCost(@RequestHeader(AUTHORIZATION) String authorization) {

		Customer customer = returnCustomerFromToken(authorization);
		
		List<Device> devices = deviceRepository.findByCustomer(customer);
		
		Map<String, Integer> hashMapDevicesQuantity = RmmUtils.returnMapQuantityOperatingSystem(devices);
				
		Integer count = RmmUtils.calculatePrice(customer, devices);
		
		return ResponseEntity.ok().body(RmmUtils.generateCostResponse(customer, hashMapDevicesQuantity, count));
	}
		
	private Customer returnCustomerFromToken(String token) {
		String jwt = token.substring(7);
		String userName = jwtUtil.extractUserName(jwt);
		return customerRepository.findByUserName(userName)
				.orElseThrow(() -> new UsernameNotFoundException("Not found" + userName));

	}
}
