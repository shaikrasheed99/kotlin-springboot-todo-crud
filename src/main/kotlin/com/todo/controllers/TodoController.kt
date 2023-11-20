package com.todo.controllers

import com.todo.constants.MessageResponses
import com.todo.dto.request.TodoRequest
import com.todo.dto.response.SuccessResponse
import com.todo.services.TodoService
import com.todo.utils.createSuccessResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TodoController(private val todoService: TodoService) {
    @PostMapping("/todos")
    fun addTodo(@Valid @RequestBody todoRequest: TodoRequest): ResponseEntity<SuccessResponse> {
        return todoService.createTodo(todoRequest).let {
            val message = MessageResponses.TODO_CREATION_SUCCESS.message
            val successResponse = createSuccessResponse(message, it)
            ResponseEntity.ok(successResponse)
        }
    }

    @GetMapping("/todos")
    fun getAllTodos(): ResponseEntity<SuccessResponse> {
        return todoService.getAllTodos().let {
            val message = MessageResponses.ALL_TODO_DETAILS.message
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

    @GetMapping("/todos/status/{status}")
    fun getTodosByStatus(@PathVariable status: String): ResponseEntity<SuccessResponse> {
        return todoService.getTodosByStatus(status).let {
            val message = MessageResponses.ALL_TODO_DETAILS.message
            val successResponse = createSuccessResponse(message, it)
            ResponseEntity.ok(successResponse)
        }
    }

    @GetMapping("/todos/priority/{priority}")
    fun getTodosByPriority(@PathVariable priority: String): ResponseEntity<SuccessResponse> {
        return todoService.getTodosByPriority(priority).let {
            val message = MessageResponses.ALL_TODO_DETAILS.message
            val successResponse = createSuccessResponse(message, it)
            ResponseEntity.ok(successResponse)
        }
    }
}