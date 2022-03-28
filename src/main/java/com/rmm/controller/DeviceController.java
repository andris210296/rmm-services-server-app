package com.rmm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.rmm.model.Customer;
import com.rmm.model.Device;
import com.rmm.repository.CustomerRepository;
import com.rmm.repository.DeviceRepository;
import com.rmm.security.JwtUtil;

@RestController
@RequestMapping({ "/device" })
public class DeviceController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@PostMapping(value = "/create")
	public Device create(@RequestHeader("Authorization") String authorization, @RequestBody Device device) {

		Customer customer = returnCustomerFromToken(authorization);
		
		device.setCustomer(customer);
		return deviceRepository.save(device);
	}

	@GetMapping(value = "/myDevices")
	public List findByCustomer(@RequestHeader("Authorization") String authorization) {
		
		Customer customer = returnCustomerFromToken(authorization);		
		
		return deviceRepository.findByCustomer(customer);
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity findById(@PathVariable long id) {
		return deviceRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity update(@PathVariable("id") long id, @RequestBody Device device) {
		return deviceRepository.findById(id).map(record -> {
			record.setSystemName(device.getSystemName());
			record.setType(device.getType());
			Device updated = deviceRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable long id) {
		return deviceRepository.findById(id).map(record -> {
			deviceRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}
	
	private Customer returnCustomerFromToken(String token) {
		String jwt = token.substring(7);		
		String userName = jwtUtil.extractUserName(jwt);
		return customerRepository.findByUserName(userName)
				.orElseThrow(() -> new UsernameNotFoundException("Not found" + userName));
		
	}
}
