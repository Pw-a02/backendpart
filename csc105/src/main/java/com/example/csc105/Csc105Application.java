package com.example.csc105;

import com.example.csc105.database.MySQLConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Csc105Application {

	public static void main(String[] args) {
		SpringApplication.run(Csc105Application.class, args);
		try {
			new MySQLConnector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
