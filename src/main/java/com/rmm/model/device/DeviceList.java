package com.rmm.model.device;

import java.io.Serializable;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeviceList implements Serializable{

	private static final long serialVersionUID = 4428287907761621587L;	

	private long id;	
	
	private String systemName;	
	
	private String type;
	

}
