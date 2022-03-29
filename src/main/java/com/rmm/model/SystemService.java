package com.rmm.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class SystemService implements Serializable {

	private static final long serialVersionUID = -3181354155520156969L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
		
	private String serviceName;
		
	@ElementCollection
	private Map<String, Integer> pricePerSystem;



}
