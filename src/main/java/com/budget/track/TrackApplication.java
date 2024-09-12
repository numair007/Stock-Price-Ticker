package com.budget.track;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrackApplication {

	public static void main(String[] args) {
		System.out.println("Recenced request ");
		SpringApplication.run(TrackApplication.class, args);
	}

}
