package com.git.Nog022.MeetHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan
public class MeetHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetHubApplication.class, args);
	}

}
