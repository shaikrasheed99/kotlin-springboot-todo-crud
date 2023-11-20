package com.todo.controllers

import com.todo.constants.MessageResponses
import com.todo.dto.request.TodoRequest
import com.todo.dto.response.SuccessResponse
import com.todo.services.TodoService
import com.todo.utils.createSuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TodoController(private val todoService: TodoService) {
    @PostMapping("/todos")
    fun addTodo(@RequestBody todoRequest: TodoRequest): ResponseEntity<SuccessResponse> {
        return todoService.createTodo(todoRequest).let {
            val message = MessageResponses.TODO_CREATION_SUCCESS.message
            val successResponse = createSuccessResponse(message, it)
            ResponseEntity.ok(successResponse)
        }
    }

    @GetMapping("/todos/{todoId}")
    fun getTodoById(@PathVariable todoId: Int): ResponseEntity<SuccessResponse> {
        return todoService.getById(todoId).let {
            val message = MessageResponses.TODO_DETAILS_BY_ID.message
            val successResponse = createSuccessResponse(message, it)
            ResponseEntity.ok(successResponse)
        }
    }
}