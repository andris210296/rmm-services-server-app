package com.rmm.model.systemservice;

import java.io.Serializable;
import java.util.Map;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SystemServiceCustomerCost implements Serializable {


	private static final long serialVersionUID = -4440207947902224896L;

	private String serviceName;
		
	private Map<String, Integer> pricePerSystem;



}
