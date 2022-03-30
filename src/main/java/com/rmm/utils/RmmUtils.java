package com.rmm.utils;

import java.util.*;

import com.rmm.model.customer.Customer;
import com.rmm.model.customer.CustomerServiceResponse;
import com.rmm.model.device.Device;
import com.rmm.model.device.DeviceList;
import com.rmm.model.device.DeviceResponse;
import com.rmm.model.systemservice.CostResponse;
import com.rmm.model.systemservice.SystemService;
import com.rmm.model.systemservice.SystemServiceCustomerCost;

public class RmmUtils {
	
	public static DeviceResponse deviceToDeviceResponse(Device device) {
		
		return DeviceResponse.builder()
				.userName(device.getCustomer().getUserName())
				.devices(Arrays.asList(deviceToDeviceList(device)))
				.build();
	}
	
	public static DeviceList deviceToDeviceList(Device device) {
		
		return DeviceList.builder()
				.id(device.getId())
				.systemName(device.getSystemName())
				.type(device.getType())
				.build();		
	}
	
	public static DeviceResponse listDeviceToListDeviceResponse(List<Device> devices){
		
		List<DeviceList> deviceLists = new ArrayList<DeviceList>();
		for (Device device : devices) {
			deviceLists.add(deviceToDeviceList(device));
		}
		
		
		return DeviceResponse.builder()
				.userName(devices.get(0).getCustomer().getUserName())
				.devices(deviceLists)
				.build();	
	}
	
	public static List<SystemServiceCustomerCost> listSystemServiceToSystemServiceCustomerCost(List<SystemService> systemServices){
		List<SystemServiceCustomerCost> systemServiceCustomerCosts = new ArrayList<SystemServiceCustomerCost>();
		
		for (SystemService systemService : systemServices) {
			systemServiceCustomerCosts.add(
					SystemServiceCustomerCost.builder()
					.serviceName(systemService.getServiceName())
					.pricePerSystem(systemService.getPricePerSystem())
					.build());
		}
		
		return systemServiceCustomerCosts;
		
	}
	
	public static CustomerServiceResponse customerToCustomerServiceResponse(Customer customer) {
		return CustomerServiceResponse.builder()
				.userName(customer.getUserName())
				.selectedServices(listSystemServiceToSystemServiceCustomerCost(customer.getSystemServices()))
				.build();
	}
	
	public static CostResponse generateCostResponse(Customer customer, Map<String, Integer> hashMapDevicesQuantity, Integer price) {
		return CostResponse.builder()
				.devicesQuantity(hashMapDevicesQuantity)
				.selectedServices(listSystemServiceToSystemServiceCustomerCost(customer.getSystemServices()))
				.totalCost(price)
				.build();
	}
	
	public static Map<String,String> createMessage(String message){
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("message", message);
		
		return hashMap;
	}
	
	public static Integer calculatePrice(Customer customer, List<Device> devices) {
		
		Map<String, Integer> hashMapDevicesQuantity = RmmUtils.returnMapQuantityOperatingSystem(devices);
		
		Integer count = Math.multiplyExact(devices.size(), 4);
		
		for (SystemService systemService : customer.getSystemServices()) {
			for(String key: hashMapDevicesQuantity.keySet()) {
				count += Math.multiplyExact(systemService.getPricePerSystem().get(key), hashMapDevicesQuantity.get(key));
				
			}			
		}
		
		return count;
		
	}
	
	public static Map<String, Integer> returnMapQuantityOperatingSystem(List<Device> devices) {
		
		int countWindows =0;
		int countMac =0;		
				
		for (Device device : devices) {
			if(device.getType().contains("Windows") || "Windows".equals(device.getType())) 
				countWindows += 1;
			else
				countMac += 1;				
		}
		
		Map<String, Integer> operatingSystems = new HashMap<String, Integer>();
		operatingSystems.put("Windows", countWindows);
		operatingSystems.put("Mac", countMac);
		
		return operatingSystems;
		
	}

}
