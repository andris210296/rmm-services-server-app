package com.rmm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rmm.model.Device;
import com.rmm.repository.DeviceRepository;

@RestController
@RequestMapping({ "/device" })
public class DeviceController {

	@Autowired
	private DeviceRepository deviceRepository;
	
	@PostMapping
	public Device create(@RequestBody Device contact){
	   return deviceRepository.save(contact);
	}

	@GetMapping
	public List findAll() {
		return deviceRepository.findAll();
	}

	@GetMapping(path = {"/{id}"})
	public ResponseEntity findById(@PathVariable long id){
	   return deviceRepository.findById(id)
	           .map(record -> ResponseEntity.ok().body(record))
	           .orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity update(@PathVariable("id") long id, @RequestBody Device device) {
	   return deviceRepository.findById(id)
	           .map(record -> {
	               record.setSystemName(device.getSystemName());
	               record.setType(device.getType());
	               Device updated = deviceRepository.save(record);
	               return ResponseEntity.ok().body(updated);
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return deviceRepository.findById(id)
	           .map(record -> {
	               deviceRepository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
}
