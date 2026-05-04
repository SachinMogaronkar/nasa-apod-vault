package com.openapi.nasa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.openapi.nasa")
public class NasaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NasaApplication.class, args);
	}
}
