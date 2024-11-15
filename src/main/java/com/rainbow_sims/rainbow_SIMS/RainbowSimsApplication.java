package com.rainbow_sims.rainbow_SIMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.rainbow_sims.rainbow_SIMS.repository")
public class RainbowSimsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RainbowSimsApplication.class, args);
	}

}
