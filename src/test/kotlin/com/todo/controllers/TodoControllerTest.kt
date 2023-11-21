package com.todo.controllers

import com.todo.constants.Messages
import com.todo.constants.StatusResponses
import com.todo.dto.request.TodoRequest
import com.todo.dto.request.UpdateTodoRequest
import com.todo.models.Todo
import com.todo.models.TodoRepository
import com.todo.utils.convertToJson
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
internal class TodoControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var todoRepository: TodoRepository

    private lateinit var todo: Todo

    @BeforeEach
    fun setUp() {
        todo = Todo(
            id = 1,
            description = "sleeping",
            status = "pending",
            priority = "high",
        ).also(todoRepository::save)
    }

    @AfterEach
    fun tearDown() {
        todoRepository.deleteAll()
    }

    @Test
    internal fun shouldBeAbleToAddNewTodo() {
        val todoRequest = TodoRequest("sleeping", "pending", "high")
        val todoRequestJson = todoRequest.convertToJson()

        mockMvc.perform(
            post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoRequestJson)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(StatusResponses.SUCCESS.name))
            .andExpect(jsonPath("$.code").value(HttpStatus.OK.name))
            .andExpect(jsonPath("$.message").value(Messages.TODO_CREATION_SUCCESS.message))
    }

    @Test
    internal fun shouldBeAbleToReturnErrorWhenRequestedDescriptionFiledIsBlank() {
        val todoRequest = TodoRequest("", "pending", "high")
        val todoRequestJson = todoRequest.convertToJson()

        mockMvc.perform(
            post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoRequestJson)
        ).andExpect(status().isBadRequest)
    }

    @Test
    internal fun shouldBeAbleToReturnErrorWhenRequestedStatusFiledIsNotValid() {
        val todoRequest = TodoRequest("sleeping", "pen", "high")
        val todoRequestJson = todoRequest.convertToJson()

        mockMvc.perform(
            post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoRequestJson)
        ).andExpect(status().isBadRequest)
    }

    @Test
    internal fun shouldBeAbleToReturnErrorWhenRequestedPriorityFiledIsNotValid() {
        val todoRequest = TodoRequest("sleeping", "pending", "hi")
        val todoRequestJson = todoRequest.convertToJson()

        mockMvc.perform(
            post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoRequestJson)
        ).andExpect(status().isBadRequest)
    }

    @Test
    internal fun shouldBeAbleToReturnTodoById() {
        mockMvc.perform(
            get("/todos/{todoId}", todo.id)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(StatusResponses.SUCCESS.name))
            .andExpect(jsonPath("$.code").value(HttpStatus.OK.name))
            .andExpect(jsonPath("$.message").value(Messages.TODO_DETAILS_BY_ID.message))
    }

    @Test
    internal fun shouldBeAbleToReturnErrorResponseWhenTodoNotFoundById() {
        mockMvc.perform(
            get("/todos/{todoId}", 2)
        ).andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(StatusResponses.ERROR.name))
            .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.name))
            .andExpect(jsonPath("$.message").value(Messages.TODO_NOT_FOUND_BY_ID.message))
    }

    @Test
    internal fun shouldBeAbleToReturnErrorWhenRequestedTodoIdIsNotValid() {
        mockMvc.perform(
            get("/todos/{todoId}", "abc")
        ).andExpect(status().isBadRequest)
    }

    @Test
    internal fun shouldBeAbleToReturnAllTodos() {
        mockMvc.perform(
            get("/todos")
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(StatusResponses.SUCCESS.name))
            .andExpect(jsonPath("$.code").value(HttpStatus.OK.name))
            .andExpect(jsonPath("$.message").value(Messages.ALL_TODO_DETAILS.message))
    }

    @Test
    internal fun shouldBeAbleToReturnTodosByStatus() {
        mockMvc.perform(
            get("/todos/status/{status}", todo.status)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(StatusResponses.SUCCESS.name))
            .andExpect(jsonPath("$.code").value(HttpStatus.OK.name))
            .andExpect(jsonPath("$.message").value(Messages.ALL_TODO_DETAILS.message))
    }

    @Test
    internal fun shouldBeAbleToReturnErrorWhenRequestedStatusIsNotValid() {
        mockMvc.perform(
            get("/todos/status/{status}", "abc123")
        ).andExpect(status().isBadRequest)
    }

    @Test
    internal fun shouldBeAbleToReturnTodosByPriority() {
        mockMvc.perform(
            get("/todos/priority/{priority}", todo.priority)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(StatusResponses.SUCCESS.name))
            .andExpect(jsonPath("$.code").value(HttpStatus.OK.name))
            .andExpect(jsonPath("$.message").value(Messages.ALL_TODO_DETAILS.message))
    }

    @Test
    internal fun shouldBeAbleToReturnErrorWhenRequestedPriorityIsNotValid() {
        mockMvc.perform(
            get("/todos/priority/{priority}", "abc123")
        ).andExpect(status().isBadRequest)
    }

    @Test
    internal fun shouldBeAbleToUpdateDescriptionOfTodo() {
        val updateTodoRequest = UpdateTodoRequest("sleeping", null, null)
        val updateTodoRequestJson = updateTodoRequest.convertToJson()

        mockMvc.perform(
            put("/todos/{todoId}", todo.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateTodoRequestJson)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(StatusResponses.SUCCESS.name))
            .andExpect(jsonPath("$.code").value(HttpStatus.OK.name))
            .andExpect(jsonPath("$.message").value(Messages.TODO_UPDATE_SUCCESS.message))
    }

    @Test
    internal fun shouldBeAbleToReturnErrorWhenRequestedTodoIdIsNotPresent() {
        mockMvc.perform(
            put("/todos/{todoId}", 100)
        ).andExpect(status().isBadRequest)
    }

    @Test
    internal fun shouldNotBeAbleToUpdateTodoWhenRequestedStatusIsNotValid() {
        val updateTodoRequest = UpdateTodoRequest("sleeping", "invalidStatus", null)
        val updateTodoRequestJson = updateTodoRequest.convertToJson()

        mockMvc.perform(
            put("/todos/{todoId}", todo.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateTodoRequestJson)
        ).andExpect(status().isBadRequest)
    }

    @Test
    internal fun shouldNotBeAbleToUpdateTodoWhenRequestedPriorityIsNotValid() {
        val updateTodoRequest = UpdateTodoRequest("sleeping", null, "invalidPriority")
        val updateTodoRequestJson = updateTodoRequest.convertToJson()

        mockMvc.perform(
            put("/todos/{todoId}", todo.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateTodoRequestJson)
        ).andExpect(status().isBadRequest)
    }
}