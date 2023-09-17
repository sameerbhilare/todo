package com.husqvarna.todo.bl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.husqvarna.todo.bl.dataaccess.repository")
@EntityScan(basePackages = {"com.husqvarna.todo.bl.dataaccess.entity"})
public class TodoBlApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoBlApplication.class, args);
	}

}
