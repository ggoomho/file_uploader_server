package com.chols.fu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FuApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuApplication.class, args);
	}

}
