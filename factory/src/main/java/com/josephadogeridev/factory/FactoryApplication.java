package com.josephadogeridev.factory;

/**
 * @author Joseph Adogeri
 * @since 25-SEP-2025
 * @version 1.0.0
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.josephadogeridev.factory"})  // scan JPA entities
public class FactoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FactoryApplication.class, args);
	}

}
