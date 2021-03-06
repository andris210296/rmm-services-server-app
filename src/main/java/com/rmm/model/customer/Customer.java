package com.rmm.model.customer;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.rmm.model.device.Device;
import com.rmm.model.systemservice.SystemService;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Customer implements Serializable {

	private static final long serialVersionUID = 5356889730511280675L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotEmpty
	private String userName;
	
	@NotEmpty
	private String password;	
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Device> devices;
	
	@OneToMany
	private List<SystemService> systemServices;
	

}
