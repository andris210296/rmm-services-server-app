package com.rmm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.rmm.repository.CustomerRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = CustomerRepository.class)
public class RmmServicesServerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmmServicesServerAppApplication.class, args);
	}

}
