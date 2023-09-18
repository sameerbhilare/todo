# Project Title

Todo REST APIs

## Description

This application exposes few REST APIs for a typical Todo application.

Refer below API section to know more about the exposed endpoints.

### Todo API Flow
Once a Todo is created, it will be in `ACTIVE` state. Once completed, the Todo will be in `COMPLETED` state.

### Architectural Decisions
* User can change the status of the Todo from `ACTIVE` to `COMPLETED` and vice-versa.
* User can rename a Todo.
* Allowing a user to delete a todo.
* Allowing a user to delete all the todos.
* Integration Testing
  * I am using `WebEnvironment.RANDOM_PORT` approach instead of `WebEnvironment.MOCK`.
  * `WebEnvironment.RANDOM_PORT` approach is closer to test the real application. It is as if you test with a real http server. 

## API

#### /api/todos
* `GET` : Get all Todos
* `POST` : Create a new Todo
* `DELETE` : Delete all Todos

#### /api/todos?status={status}
* `GET` : Get all Todos as per status. Allowed values for status are - `ACTIVE`, `COMPLETED`.

#### /api/todos/:id
* `GET` : Get a single Todo
* `PUT` : Update an existing Todo
* `DELETE` : Delete an existing Todo

## Prerequisite
* Java 17
* Maven

## Executing program
Make sure you have the prerequisites mentioned above.

Execute below command from root of this project. It will build and run the application on your local.
```
start-server.cmd
```

### Way 1: Using Swagger UI

Once the application is started, hit below Swagger UI URL and test the APIs.
```
http://localhost:8080/swagger-ui/index.html
```

### Way 2: Using Postman
Prerequisite - You need to use either Postman app or Postman web for this.
* Import attached workspace `todo-api.postman_collection.json` in the Postman.
* Test all the exposed endpoints.

## Author
[@sameerbhilare](https://github.com/sameerbhilare)

## Version History
* 1.0
    * Initial Release
