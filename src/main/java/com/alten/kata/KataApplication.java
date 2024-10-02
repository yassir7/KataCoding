package com.alten.kata;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Kata REST API Documentation",
				description = "Kata app REST API Documentation",
				version = "v1",
				contact= @Contact(
						name = "Yassir Abouzid",
						email = "abzyassir@gmail.com"
				)
		)
)
public class KataApplication {

	public static void main(String[] args) {
		SpringApplication.run(KataApplication.class, args);
	}

}
