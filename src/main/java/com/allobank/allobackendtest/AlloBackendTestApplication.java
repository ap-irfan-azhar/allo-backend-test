package com.allobank.allobackendtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AlloBackendTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlloBackendTestApplication.class, args);
	}

}
