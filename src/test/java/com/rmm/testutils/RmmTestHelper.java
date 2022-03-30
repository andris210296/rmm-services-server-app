package com.rmm.testutils;

import static org.mockito.Mockito.when;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import com.rmm.model.*;
import com.rmm.model.authentication.AuthenticationResquest;
import com.rmm.model.authentication.MyUserDetails;
import com.rmm.model.customer.Customer;
import com.rmm.model.device.Device;
import com.rmm.model.systemservice.SystemService;
import com.rmm.repository.CustomerRepository;
import com.rmm.security.JwtUtil;
import com.rmm.security.MyUserDetailsService;

public class RmmTestHelper {
		 
    @Autowired
    private JwtUtil jwtUtil;
    
    @MockBean
	protected CustomerRepository customerRepository;
    
    @MockBean
	protected MyUserDetailsService myUserDetailsService;
    
    public static final String AUTHORIZATION = "Authorization";
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	
	public static final String SYSTEM_NAME = "systemName";
	public static final String TYPE = "type";
	public static final String SERVER_1 = "Server 1";
	public static final String WINDOWS_SERVER = "Windows Server";
	public static final String WINDOWS = "Windows";
	public static final String MAC_1 = "Mac 1";
	public static final String MAC = "Mac";
	
	public static final String SERVICE_NAME = "serviceName";
	public static final String ANTIVIRUS = "Antivirus";
	public static final String PSA = "PSA";
	public static final String TEAMVIEWER = "TeamViewer";

	public String generateBearerToken() throws Exception {

		when(customerRepository.findByUserName(USER_NAME)).thenReturn(generateOptionalCustomer());
		
		when(myUserDetailsService.loadUserByUsername(USER_NAME))
				.thenReturn(generateUserDetails());

		return "Bearer " + jwtUtil.generateToken(generateUserDetails());
	}
	
	public UserDetails generateUserDetails() {
		return new MyUserDetails().builder()
				.userName(USER_NAME)
				.password(PASSWORD)
				.build();		
	}

	public AuthenticationResquest generateAuthenticationResquest() {
		return new AuthenticationResquest().builder()
				.userName(USER_NAME)
				.password(PASSWORD)
				.build();				
	}
	
	public HashMap<String, String> generateHashMapCustomerWithoutId() {
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(USER_NAME, USER_NAME);
		hashMap.put(PASSWORD, PASSWORD);
		
		return hashMap;		
	}
	
	public HashMap<String, String> generateHashMapWrong() {
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(WINDOWS, USER_NAME);
		
		
		return hashMap;		
	}
	
	public HashMap<String, String> generateHashMapDeviceWithoutId() {
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(SYSTEM_NAME, SERVER_1);
		hashMap.put(TYPE, WINDOWS_SERVER);
		
		return hashMap;		
	}
	
	public HashMap<String, String> generateHashMapDeviceDifferent() {
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(SYSTEM_NAME, MAC_1);
		hashMap.put(TYPE, MAC);
		
		return hashMap;		
	}
	
	public HashMap<String, String> generateHashMapServiceRequest() {
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(SERVICE_NAME, ANTIVIRUS);		
		return hashMap;		
	}
	
	public HashMap<String, String> generateHashMapServiceRequestTeamViewer() {
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(SERVICE_NAME, TEAMVIEWER);		
		return hashMap;		
	}
	
	public Customer generateCustomerWithoutId() {
		
		Customer customer = new Customer();
		customer.setUserName(USER_NAME);
		customer.setPassword(PASSWORD);
		
		return customer;
	}
	
	public Device generateDeviceWithoutId() {
		
		Device device = new Device();
		device.setSystemName(SERVER_1);
		device.setType(WINDOWS_SERVER);
		
		return device;
	}
		
	
	public Customer generateCustomer() {
		return 	Customer.builder()
				.id(1)
				.userName(USER_NAME)
				.password(PASSWORD)
				.devices(generateListDevices())
				.systemServices(generateListSystemService())
				.build();
	}
	
	public Device generateDevice() {
		return 	Device.builder()
				.id(1)
				.systemName(SERVER_1)
				.type(WINDOWS_SERVER)
				.customer(generateCustomer())
				.build();
	}
	
	public Optional<Device> generateOptionalDevice() {
	
		return Optional.of(
				Device.builder()
				.id(1)
				.systemName(SERVER_1)
				.type(WINDOWS_SERVER)
				.customer(generateCustomer())
				.build());
	}
	
	public Optional<SystemService> generateOptionalSystemService() {
		
		HashMap<String, Integer> difValues = new HashMap<String, Integer>();
		difValues.put(WINDOWS, 5);
		difValues.put(MAC, 7);
		
		return Optional.of(
				SystemService.builder()
				.id(1)
				.serviceName(ANTIVIRUS)
				.pricePerSystem(difValues)
				.build());
	}
	
	public Optional<SystemService> generateOptionalSystemServiceTeamViwer() {
		
		HashMap<String, Integer> difValues = new HashMap<String, Integer>();
		difValues.put(WINDOWS, 5);
		difValues.put(MAC, 7);
		
		return Optional.of(
				SystemService.builder()
				.id(1)
				.serviceName(TEAMVIEWER)
				.pricePerSystem(difValues)
				.build());
	}
	
	public List<Customer> generateListCustomers(){
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(generateCustomer());
		
		return customers;
	}
	
		
	public Optional<Customer> generateOptionalCustomer() {
		return Optional.of(
				Customer.builder()
				.id(1)
				.userName(USER_NAME)
				.password(PASSWORD)
				.devices(generateListDevices())
				.systemServices(generateListSystemService())
				.build());
	}
	
	public List<Device> generateListDevices(){
		List<Device> devices = new ArrayList<Device>();
		
		devices.add(
				Device.builder()
					.id(1)
					.systemName(SERVER_1)
					.type(WINDOWS_SERVER)
					.build()
					);
		
		devices.add(
				Device.builder()
					.id(2)
					.systemName(MAC_1)
					.type(MAC)
					.build()
					);		
		
		return devices;
	}
	
	public List<SystemService> generateListSystemService(){
		
		HashMap<String, Integer> difValues = new HashMap<String, Integer>();
		difValues.put(WINDOWS, 5);
		difValues.put(MAC, 7);

		
		List<SystemService> systemServices = new ArrayList<SystemService>();
		
		systemServices.add(
				SystemService.builder()
					.id(1)
					.serviceName(ANTIVIRUS)
					.pricePerSystem(difValues)
				.build());
		
		
		
		HashMap<String, Integer> sameValues = new HashMap<String, Integer>();
		sameValues.put(WINDOWS, 2);
		sameValues.put(MAC, 2);
		
		systemServices.add(
				SystemService.builder()
					.id(2)
					.serviceName(PSA)
					.pricePerSystem(sameValues)
				.build());
		
		
		return systemServices;
	}

}
