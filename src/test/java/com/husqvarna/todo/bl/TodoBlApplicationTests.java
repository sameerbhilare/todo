package com.husqvarna.todo.bl;

import com.husqvarna.todo.bl.vo.TodoVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Testing all the endpoints of the TODO_API.
 * We are using WebEnvironment.RANDOM_PORT approach instead of WebEnvironment.MOCK.
 * WebEnvironment.RANDOM_PORT approach is closer to test the real application. It is as if you test with a real http server.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoBlApplicationTests {

	@Value(value="${local.server.port}")
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	/**
	 * Testing for GET /api/todos endpoint
	 */
	@Test
	public void getAllTodos() {

		List<TodoVO> todos = this.restTemplate.getForObject("http://localhost:" + port + "/api/todos", List.class);
		Assert.isTrue(todos.size() == 3, "Expected todos size=3. Actual=" + todos.size());
	}

	/**
	 * Testing for GET /api/todos/{id} endpoint
	 */
	@Test
	public void getTodo() {

		// create new task to be saved
		String expectedTodoName = "Test create todo";
		String expectedTodoStatus = "ACTIVE";
		TodoVO todoToBeSaved = TodoVO.builder()
				.name(expectedTodoName)
				.build();

		// save the new task
		TodoVO savedTodo = this.restTemplate.postForObject("http://localhost:" + port + "/api/todos", todoToBeSaved, TodoVO.class);
		Long expectedTodoId = savedTodo.getId();

		// call GET api to fetch for one
		TodoVO refetchedTodo = this.restTemplate.getForObject("http://localhost:" + port + "/api/todos/" + expectedTodoId, TodoVO.class);

		Assert.isTrue(Objects.equals(refetchedTodo.getId(), expectedTodoId), "Expected Todo Id=" + expectedTodoId + ". Actual=" + expectedTodoId);
		Assert.isTrue(refetchedTodo.getName().equals(expectedTodoName), "Expected Todo name=" + expectedTodoName + ". Actual=" + refetchedTodo.getName());
		Assert.isTrue(refetchedTodo.getStatus().equals(expectedTodoStatus), "Expected Todo status=" + expectedTodoStatus + ". Actual=" + refetchedTodo.getStatus());
	}

	/**
	 * Testing for POST /api/todos endpoint
	 */
	@Test
	public void createTodo() {

		// create new task to be saved
		String testTodoName = "Test create todo";
		TodoVO todoToBeSaved = TodoVO.builder()
				.name(testTodoName)
				.build();

		// save the new task
		TodoVO savedTodo = this.restTemplate.postForObject("http://localhost:" + port + "/api/todos", todoToBeSaved, TodoVO.class);

		String expectedTodoStatus = "ACTIVE";
		Assert.isTrue(savedTodo.getName().equals(testTodoName), "Expected Todo name=" + testTodoName + ". Actual=" + savedTodo.getName());
		Assert.isTrue(savedTodo.getStatus().equals(expectedTodoStatus), "Expected Todo status=" + expectedTodoStatus + ". Actual=" + savedTodo.getStatus());
	}

	/**
	 * Testing for PUT /api/todos/{id} endpoint
	 */
	@Test
	public void updateTodo() {

		// create new task to be saved
		String testTodoName = "Test create todo";
		TodoVO todoToBeSaved = TodoVO.builder()
				.name(testTodoName)
				.build();

		// save the new task
		TodoVO savedTodo = this.restTemplate.postForObject("http://localhost:" + port + "/api/todos", todoToBeSaved, TodoVO.class);

		// get existing task
		Long expectedTodoId = savedTodo.getId();
		TodoVO todoToBeUpdated = this.restTemplate.getForObject("http://localhost:" + port + "/api/todos/" + expectedTodoId, TodoVO.class);

		String expectedTodoName = "Updated Todo Name";
		String expectedTodoStatus = "COMPLETED";
		todoToBeUpdated.setName(expectedTodoName);
		todoToBeUpdated.setStatus(expectedTodoStatus);

		// save task
		HttpEntity<TodoVO> requestEntity = new HttpEntity<>(todoToBeUpdated, null);
		ResponseEntity<TodoVO> responseEntity = this.restTemplate.exchange("http://localhost:" + port + "/api/todos/" + expectedTodoId,
				HttpMethod.PUT, requestEntity, TodoVO.class, new HashMap<>());

		Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT),  "Expected HTTP Status=" + HttpStatus.NO_CONTENT.value()
				+ ". Actual=" + responseEntity.getStatusCode());

		// fetch the task again
		TodoVO updatedTodo = this.restTemplate.getForObject("http://localhost:" + port + "/api/todos/" + expectedTodoId, TodoVO.class);

		// assertions
		Assert.isTrue(Objects.equals(updatedTodo.getId(), expectedTodoId), "Expected Todo Id=" + expectedTodoId + ". Actual=" + expectedTodoId);
		Assert.isTrue(updatedTodo.getName().equals(expectedTodoName), "Expected Todo name=" + expectedTodoName + ". Actual=" + todoToBeUpdated.getName());
		Assert.isTrue(updatedTodo.getStatus().equals(expectedTodoStatus), "Expected Todo status=" + expectedTodoStatus + ". Actual=" + todoToBeUpdated.getStatus());
	}

	/**
	 * Testing for DELETE /api/todos/{id} endpoint
	 */
	@Test
	public void deleteTodo() {

		// create new task to be saved
		TodoVO todoToBeSaved = TodoVO.builder()
				.name("Test create todo")
				.build();

		// save the new task
		TodoVO savedTodo = this.restTemplate.postForObject("http://localhost:" + port + "/api/todos", todoToBeSaved, TodoVO.class);

		// delete the task
		HttpEntity<TodoVO> requestEntity = new HttpEntity<>(savedTodo, null);
		ResponseEntity<TodoVO> responseEntity  = restTemplate.exchange("http://localhost:" + port + "/api/todos/" + savedTodo.getId(),
				HttpMethod.DELETE, requestEntity, TodoVO.class);

		// assertions
		Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT),  "Expected HTTP Status=" + HttpStatus.NO_CONTENT.value()
				+ ". Actual=" + responseEntity.getStatusCode());
	}

	/**
	 * Testing for GET /api/todos endpoint
	 */
	@Test
	public void deleteAllTodos() {

		// create new task to be saved
		TodoVO todoToBeSaved = TodoVO.builder()
				.name("Test create todo")
				.build();

		// save the new task
		TodoVO savedTodo = this.restTemplate.postForObject("http://localhost:" + port + "/api/todos", todoToBeSaved, TodoVO.class);

		// delete all tasks
		HttpEntity<TodoVO> requestEntity = new HttpEntity<>(null, null);
		ResponseEntity<TodoVO> responseEntity  = restTemplate.exchange("http://localhost:" + port + "/api/todos",
				HttpMethod.DELETE, requestEntity, TodoVO.class);

		// assertions
		Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT),  "Expected HTTP Status=" + HttpStatus.NO_CONTENT.value()
				+ ". Actual=" + responseEntity.getStatusCode());

		// fetch all tasks, they should be 0.
		List<TodoVO> todos = this.restTemplate.getForObject("http://localhost:" + port + "/api/todos", List.class);
		Assert.isTrue(todos.size() == 0, "Expected todos size=0. Actual=" + todos.size());
	}

}
