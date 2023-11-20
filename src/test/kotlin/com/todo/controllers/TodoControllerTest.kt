package com.todo.controllers

import com.todo.constants.MessageResponses
import com.todo.constants.StatusResponses
import com.todo.dto.request.TodoRequest
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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
        todo.id?.let(todoRepository::deleteById)
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
            .andExpect(jsonPath("$.message").value(MessageResponses.TODO_CREATION_SUCCESS.message))
    }

    @Test
    internal fun shouldBeAbleToReturnTodoById() {
        mockMvc.perform(
            get("/todos/{todoId}", 1)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(StatusResponses.SUCCESS.name))
            .andExpect(jsonPath("$.code").value(HttpStatus.OK.name))
            .andExpect(jsonPath("$.message").value(MessageResponses.TODO_DETAILS_BY_ID.message))
    }

    @Test
    internal fun shouldBeAbleToReturnErrorResponseWhenTodoNotFoundById() {
        mockMvc.perform(
            get("/todos/{todoId}", 2)
        ).andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(StatusResponses.ERROR.name))
            .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.name))
            .andExpect(jsonPath("$.message").value(MessageResponses.TODO_NOT_FOUND_BY_ID.message))
    }

    @Test
    internal fun shouldBeAbleToReturnAllTodos() {
        mockMvc.perform(
            get("/todos")
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(StatusResponses.SUCCESS.name))
            .andExpect(jsonPath("$.code").value(HttpStatus.OK.name))
            .andExpect(jsonPath("$.message").value(MessageResponses.ALL_TODO_DETAILS.message))
    }
}