package com.unidates.Unidates.UniDates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class UniDatesApplication {
	public static void main(String[] args) {
		SpringApplication.run(UniDatesApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate(){return new RestTemplate();}
}
