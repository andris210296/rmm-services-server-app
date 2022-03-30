package com.rmm.model.authentication;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rmm.model.customer.Customer;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MyUserDetails implements UserDetails{
	
	private String userName;
	private String password;

	

	public MyUserDetails(Customer customer) {
		this.userName = customer.getUserName();
		this.password = customer.getPassword();
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
	

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	
}
