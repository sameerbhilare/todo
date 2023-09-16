package com.husqvarna.todo.bl;

import com.husqvarna.todo.bl.controller.TodoController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoBlApplicationTests {

	@Autowired
	private TodoController todoController;

	@Value(value="${local.server.port}")
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		Assertions.assertThat(todoController).isNotNull();
	}

	@Test
	public void getAllTodos() throws Exception {
		System.out.println(this.restTemplate.getForObject("http://localhost:" + port + "/api/todos",
				List.class));
	}

}
