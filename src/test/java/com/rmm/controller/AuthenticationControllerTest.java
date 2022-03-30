package com.rmm.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmm.testutils.RmmTestHelper;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest extends RmmTestHelper{
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper mapper;
	
	@MockBean
	private AuthenticationManager authenticationManager; 
	
	@Test
	public void createAthenticationTokenTest() throws Exception {
		
		when(customerRepository.findByUserName(USER_NAME)).thenReturn(generateOptionalCustomer());		
		when(myUserDetailsService.loadUserByUsername(USER_NAME)).thenReturn(generateUserDetails());
		
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders
				.post("/authenticate")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(generateAuthenticationResquest())))
				.andExpect(status().isOk());
		
		String resultString = result.andReturn().getResponse().getContentAsString();

	    assertNotNull(resultString);	
	}
	
	@Test
	public void createAthenticationTokenBadCredentialsExceptionTest() throws Exception {
			
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
			.thenThrow(BadCredentialsException.class);
			
		mockMvc.perform(MockMvcRequestBuilders
				.post("/authenticate")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(generateAuthenticationResquest())))
				.andExpect(status().isForbidden());		
			
	}

}
