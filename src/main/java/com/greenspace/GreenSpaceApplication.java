package com.greenspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GreenSpaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenSpaceApplication.class, args);
	}

}
