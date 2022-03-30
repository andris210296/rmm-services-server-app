package com.rmm.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rmm.model.authentication.MyUserDetails;
import com.rmm.model.customer.Customer;
import com.rmm.repository.CustomerRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {		
		Optional<Customer> customer = customerRepository.findByUserName(userName);
		
		customer.orElseThrow(()-> new UsernameNotFoundException("Not found" + userName));
		
		return customer.map(MyUserDetails::new).get();
	}

}
