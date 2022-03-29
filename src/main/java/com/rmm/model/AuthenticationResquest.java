package com.rmm.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthenticationResquest {
	
	private String userName;
	private String password;

}
