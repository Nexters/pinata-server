package com.nexters.pinataserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PinataServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PinataServerApplication.class, args);
	}

}
