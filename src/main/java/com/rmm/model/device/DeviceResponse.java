package com.rmm.model.device;

import java.io.Serializable;
import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeviceResponse implements Serializable{

	private static final long serialVersionUID = 4428287907761621587L;	

	private String userName;
	
	private List<DeviceList>  devices;

}
