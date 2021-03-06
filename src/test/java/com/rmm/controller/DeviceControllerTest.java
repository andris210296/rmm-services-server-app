package com.rmm.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmm.model.device.DeviceResponse;
import com.rmm.repository.DeviceRepository;
import com.rmm.testutils.RmmTestHelper;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceControllerTest extends RmmTestHelper{
	
	@Autowired
    private MockMvc mockMvc;
	
    @Autowired
    private ObjectMapper mapper;
    
    @MockBean
    private DeviceRepository deviceRepository;   
    
    @Test
    public void createTest() throws Exception {
    	
    	when(deviceRepository.save(generateDeviceWithoutId())).thenReturn(generateDevice());
    	    	
    	 mockMvc.perform(MockMvcRequestBuilders
    			.post("/device/create")
    			.header(AUTHORIZATION, generateBearerToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(generateHashMapDeviceWithoutId())))
                .andExpect(status().isOk());         	
    }
    
    @Test
    public void findByCustomerTest() throws Exception {        
    	
    	when(deviceRepository.findByCustomer(generateCustomer())).thenReturn(generateListDevices());
        
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/device/myDevices")
                .header(AUTHORIZATION, generateBearerToken())
                .contentType(MediaType.APPLICATION_JSON))        		
                .andExpect(status().isOk());
        
        String resultString = result.andReturn().getResponse().getContentAsString();
        
        DeviceResponse device = mapper.readValue(resultString, DeviceResponse.class);

       assertEquals(SERVER_1, device.getDevices().get(0).getSystemName());
    }
    
    @Test
    public void findByIdTest() throws Exception {        
    	
    	when(deviceRepository.findById(1l)).thenReturn(generateOptionalDevice());
        
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/device/myDevices/1")
                .header(AUTHORIZATION, generateBearerToken())
                .contentType(MediaType.APPLICATION_JSON))        		
                .andExpect(status().isOk());
        
        String resultString = result.andReturn().getResponse().getContentAsString();
        
        DeviceResponse device = mapper.readValue(resultString, DeviceResponse.class);

       assertEquals(SERVER_1, device.getDevices().get(0).getSystemName());
    }
    
    @Test
    public void deleteTest() throws Exception {        
    	
    	when(deviceRepository.findById(1l)).thenReturn(generateOptionalDevice());
        
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/device/myDevices/1")
                .header(AUTHORIZATION, generateBearerToken())
                .contentType(MediaType.APPLICATION_JSON))                     		
                .andExpect(status().isOk());       
        
    }     
    
}
