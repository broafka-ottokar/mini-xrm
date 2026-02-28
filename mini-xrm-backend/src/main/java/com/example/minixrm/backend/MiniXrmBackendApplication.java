package com.example.minixrm.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class MiniXrmBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniXrmBackendApplication.class, args);
	}

}