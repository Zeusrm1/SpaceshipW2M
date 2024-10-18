package com.example.SpaceshipW2M;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpaceshipW2MApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceshipW2MApplication.class, args);
	}

}
