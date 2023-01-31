package com.NWT_KTS_project;

import com.NWT_KTS_project.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.Scanner;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}, scanBasePackages = {"com"})
public class NwtKtsProjectApplication {

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

	public static void main(String[] args) {
		SpringApplication.run(NwtKtsProjectApplication.class, args);
	}

}
