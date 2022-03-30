package com.rmm.model.customer;

import java.io.Serializable;
import java.util.List;

import com.rmm.model.systemservice.SystemServiceCustomerCost;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerServiceResponse implements Serializable {

	private static final long serialVersionUID = -7573261624292137785L;
	
	private String userName;
	
	private List<SystemServiceCustomerCost> selectedServices;
	

}
