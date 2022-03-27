package com.rmm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rmm.model.Customer;
import com.rmm.repository.CustomerRepository;

@RestController
@RequestMapping({ "/customer" })
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	@PostMapping
	public Customer create(@RequestBody Customer customer){
	   return customerRepository.save(customer);
	}

	@GetMapping
	public List findAll() {
		return customerRepository.findAll();
	}


	@PutMapping(value="/{id}")
	public ResponseEntity update(@PathVariable("id") long id, @RequestBody Customer customer) {
	   return customerRepository.findById(id)
	           .map(record -> {
	               record.setLogin(customer.getLogin());
	               record.setPassword(customer.getPassword());
	               Customer updated = customerRepository.save(customer);
	               return ResponseEntity.ok().body(updated);
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return customerRepository.findById(id)
	           .map(record -> {
	               customerRepository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
}
