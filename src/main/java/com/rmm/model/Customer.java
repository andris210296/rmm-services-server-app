package com.rmm.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
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
	
	private String roles;
	
	private boolean active;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Device> devices;
	

}
