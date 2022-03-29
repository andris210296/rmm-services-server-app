package com.rmm.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmm.model.Customer;
import com.rmm.testutils.RmmTestHelper;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest extends RmmTestHelper{
	
	@Autowired
    private MockMvc mockMvc;
	
    @Autowired
    private ObjectMapper mapper;    
         
    @Test
    public void createTest() throws Exception {
    	
    	when(customerRepository.save(generateCustomerWithoutId())).thenReturn(generateCustomer());
    	    	
    	ResultActions result = mockMvc.perform(MockMvcRequestBuilders
    			.post("/customer/create")
    			.header(AUTHORIZATION, generateBearerToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(generateHashMapCustomerWithoutId())))
                .andExpect(status().isOk());
        
        String resultString = result.andReturn().getResponse().getContentAsString();
        
        Customer customer = mapper.readValue(resultString,Customer.class);

        assertEquals(1, customer.getId());
        assertEquals(USER_NAME, customer.getUserName());    	
    }
        
    @Test
    public void findAllTest() throws Exception {        
    	
        when(customerRepository.findAll()).thenReturn(generateListCustomers());
        
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/customer/getAllCustomers")
                .header(AUTHORIZATION, generateBearerToken())
                .contentType(MediaType.APPLICATION_JSON))        		
                .andExpect(status().isOk());
        
        String resultString = result.andReturn().getResponse().getContentAsString();
        
        List<Customer> customer = mapper.readValue(resultString, new TypeReference<List<Customer>>(){});

       assertEquals(USER_NAME, customer.get(0).getUserName());
    }
    
    
}
