package com.example.ServiceBookingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan("com.example.ServiceBookingSystem")
public class ServiceBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceBookingSystemApplication.class, args);
		System.out.println("service is available");
	}

}