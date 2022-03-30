package com.rmm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.rmm.model.customer.Customer;
import com.rmm.model.device.Device;
import com.rmm.model.device.DeviceResponse;
import com.rmm.repository.CustomerRepository;
import com.rmm.repository.DeviceRepository;
import com.rmm.security.JwtUtil;
import com.rmm.utils.RmmUtils;

@RestController
@RequestMapping({ "/device" })
public class DeviceController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private DeviceRepository deviceRepository;
	
	private static final String AUTHORIZATION = "Authorization";

	@PostMapping(value = "/create")
	public DeviceResponse create(@RequestHeader(AUTHORIZATION) String authorization, @RequestBody Device device) {

		Customer customer = returnCustomerFromToken(authorization);

		device.setCustomer(customer);
		deviceRepository.save(device);
		
		return RmmUtils.deviceToDeviceResponse(device);
	}

	@GetMapping(value = "/myDevices")
	public DeviceResponse findByCustomer(@RequestHeader(AUTHORIZATION) String authorization) {

		Customer customer = returnCustomerFromToken(authorization);

		List<Device> devices = deviceRepository.findByCustomer(customer);
		return RmmUtils.listDeviceToListDeviceResponse(devices);
	}

	@GetMapping(path = { "/myDevices/{id}" })
	public ResponseEntity findById(@RequestHeader(AUTHORIZATION) String authorization, @PathVariable long id) {

		Customer customer = returnCustomerFromToken(authorization);

		return deviceRepository.findById(id).filter(device -> device.getCustomer().getId() == customer.getId())
				.map(device -> ResponseEntity.ok().body(RmmUtils.deviceToDeviceResponse(device))).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping(value = "/myDevices/{id}")
	public ResponseEntity update(@RequestHeader(AUTHORIZATION) String authorization, @PathVariable("id") long id,
			@RequestBody Device device) {

		Customer customer = returnCustomerFromToken(authorization);

		return deviceRepository.findById(id).filter(record -> record.getCustomer().getId() == customer.getId())
				.map(record -> {
					record.setSystemName(device.getSystemName());
					record.setType(device.getType());
					Device updated = deviceRepository.save(record);
					return ResponseEntity.ok().body(RmmUtils.deviceToDeviceResponse(updated));
				}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "/myDevices/{id}" })
	public ResponseEntity<?> delete(@RequestHeader(AUTHORIZATION) String authorization, @PathVariable long id) {

		Customer customer = returnCustomerFromToken(authorization);

		return deviceRepository.findById(id).filter(record -> record.getCustomer().getId() == customer.getId())
				.map(record -> {
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
