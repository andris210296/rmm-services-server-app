package com.rmm.controller;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmm.model.SystemService;
import com.rmm.repository.CustomerRepository;
import com.rmm.repository.DeviceRepository;
import com.rmm.repository.SystemServiceRepository;
import com.rmm.security.JwtUtil;
import com.rmm.testutils.RmmTestHelper;

@SpringBootTest
@AutoConfigureMockMvc
public class SystemServiceControllerTest extends RmmTestHelper {
	
	@Mock
	private JwtUtil jwtUtil;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@MockBean
	private DeviceRepository deviceRepository;
	
	@InjectMocks
	private SystemServiceController systemServiceController;
	
	@Autowired
    private MockMvc mockMvc;
	
    @Autowired
    private ObjectMapper mapper;
    
    @MockBean
    private SystemServiceRepository systemServiceRepository;  
	
	@Test
    public void addServiceTest() throws Exception {
    	
    	when(systemServiceRepository.findByServiceName(TEAMVIEWER)).thenReturn(generateOptionalSystemServiceTeamViwer());
    	    	
    	mockMvc.perform(MockMvcRequestBuilders
    			.post("/systemService/addService")
    			.header(AUTHORIZATION, generateBearerToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(generateHashMapServiceRequestTeamViewer())))
                .andExpect(status().isOk());      
         	
    }
	
	@Test
    public void findAllMyServicesTest() throws Exception {        
	        
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/systemService/myServices")
                .header(AUTHORIZATION, generateBearerToken())
                .contentType(MediaType.APPLICATION_JSON))        		
                .andExpect(status().isOk());
        
        String resultString = result.andReturn().getResponse().getContentAsString();
        
        List<SystemService> systemService = mapper.readValue(resultString, new TypeReference<List<SystemService>>(){});

       assertEquals(ANTIVIRUS, systemService.get(0).getServiceName());
    }
	
	@Test
    public void deleteTest() throws Exception {        
    	
		when(systemServiceRepository.findByServiceName(ANTIVIRUS)).thenReturn(generateOptionalSystemService());
        
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/systemService/myServices/Antivirus")
                .header(AUTHORIZATION, generateBearerToken())
                .contentType(MediaType.APPLICATION_JSON))                     		
                .andExpect(status().isOk());       
        
    }
	
	@Test
	public void calculateCostTest() throws Exception {
		when(jwtUtil.extractUserName(USER_NAME)).thenReturn(USER_NAME);
		when(customerRepository.findByUserName(USER_NAME)).thenReturn(generateOptionalCustomer());
		when(deviceRepository.findByCustomer(generateCustomer())).thenReturn(generateListDevices());
		
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/systemService/myServices/cost")
                .header(AUTHORIZATION, generateBearerToken())
                .contentType(MediaType.APPLICATION_JSON))        		
                .andExpect(status().isOk());
        
        String resultString = result.andReturn().getResponse().getContentAsString();
 
       assertEquals(String.valueOf(24), resultString);			
	}
	
	@Test
	public void returnMapQuantityOperatingSystemTest() {
		Map<String, Integer> response = systemServiceController.returnMapQuantityOperatingSystem(generateListDevices());
		
		int a = response.get(WINDOWS);
		
		assertEquals(1, a);

	}
	
	

}
