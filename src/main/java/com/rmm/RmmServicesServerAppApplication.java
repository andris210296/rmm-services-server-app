package com.rmm;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import com.rmm.model.systemservice.SystemService;
import com.rmm.repository.CustomerRepository;
import com.rmm.repository.SystemServiceRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = CustomerRepository.class)
public class RmmServicesServerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmmServicesServerAppApplication.class, args);
	}
	
	@Component
	class DemoCommandLineRunner implements CommandLineRunner{

		@Autowired
		private SystemServiceRepository systemServiceRepository;

		@Override
		public void run(String... args) throws Exception {
			
			if(systemServiceRepository.findAll().size() != 4) {			
			
				systemServiceRepository.save(new SystemService().builder()
						.id(1)
						.serviceName("Antivirus")
						.pricePerSystem(Map.of("Windows", 5, "Mac", 7))
						.build());
				
				
				systemServiceRepository.save(new SystemService().builder()
						.id(2)
						.serviceName("Cloudberry")
						.pricePerSystem(Map.of("Windows", 3, "Mac", 3))
						.build());
				
				systemServiceRepository.save( new SystemService().builder()
						.id(3)
						.serviceName("PSA")
						.pricePerSystem(Map.of("Windows", 2, "Mac", 2))
						.build());
				
				systemServiceRepository.save(new SystemService().builder()
						.id(4)
						.serviceName("TeamViewer")
						.pricePerSystem(Map.of("Windows", 1, "Mac", 1))
						.build());
			}
			

			
		}
	}
	
}
