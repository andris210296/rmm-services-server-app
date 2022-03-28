package com.rmm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rmm.model.AuthenticationResponse;
import com.rmm.model.AuthenticationResquest;
import com.rmm.security.JwtUtil;
import com.rmm.security.MyUserDetailsService;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;

	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAthenticationToken(@RequestBody AuthenticationResquest aunthentication) throws Exception {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(aunthentication.getUserName(), aunthentication.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect login or password", e);
		}
		
		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(aunthentication.getUserName());
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
