package com.todo.controllers

import com.todo.constants.MessageResponses
import com.todo.constants.StatusResponses
import com.todo.dto.request.TodoRequest
import com.todo.utils.convertToJson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
internal class TodoControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    internal fun shouldBeAbleToAddNewTodo() {
        val todoRequest = TodoRequest("sleeping", "pending", "high")
        val todoRequestJson = todoRequest.convertToJson()

        mockMvc.perform(
            post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoRequestJson)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.message").value(MessageResponses.TODO_CREATION_SUCCESS.message))
            .assertCommonJsonAssertions()
    }

    private fun ResultActions.assertCommonJsonAssertions() {
        andExpect(jsonPath("$.status").value(StatusResponses.SUCCESS.name))
            .andExpect(jsonPath("$.code").value(HttpStatus.OK.name))
    }
}