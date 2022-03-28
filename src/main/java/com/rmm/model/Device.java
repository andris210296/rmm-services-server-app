package com.rmm.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Device implements Serializable {

	private static final long serialVersionUID = -7360685728419907371L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotEmpty
	private String systemName;
	
	@NotEmpty
	private String type;
	
	@ManyToOne
	private Customer customer;

}
