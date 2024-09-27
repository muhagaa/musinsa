package com.musinsa.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemApplication.class, args);
	}

}
