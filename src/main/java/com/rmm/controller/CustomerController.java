package com.rmm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rmm.model.Customer;
import com.rmm.repository.CustomerRepository;

@RestController
@RequestMapping({ "/customer" })
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@PostMapping(value = "/create")
	public Customer create(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}

	@GetMapping
	public List findAll() {
		return customerRepository.findAll();
	}
}
