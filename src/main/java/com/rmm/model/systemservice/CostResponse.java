package com.rmm.model.systemservice;

import java.util.List;
import java.util.Map;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CostResponse {
	
	private Map<String, Integer> devicesQuantity;
	
	private List<SystemServiceCustomerCost> selectedServices;
	
	private int totalCost;

}
