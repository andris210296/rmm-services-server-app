package com.rmm.model.device;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.rmm.model.customer.Customer;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
