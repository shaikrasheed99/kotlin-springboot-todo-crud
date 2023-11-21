package com.todo.controllers

import com.todo.constants.Messages
import com.todo.dto.request.TodoRequest
import com.todo.dto.request.UpdateTodoRequest
import com.todo.dto.response.SuccessResponse
import com.todo.services.TodoService
import com.todo.utils.createSuccessResponse
import com.todo.utils.validatePriority
import com.todo.utils.validateStatus
import com.todo.utils.validateTodoId
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TodoController(private val todoService: TodoService) {
    @PostMapping("/todos")
    fun addTodo(@Valid @RequestBody todoRequest: TodoRequest): ResponseEntity<SuccessResponse> {
        return todoService.createTodo(todoRequest).let {
            val messages = Messages.TODO_CREATION_SUCCESS.message
            val successResponse = createSuccessResponse(messages, it)
            ResponseEntity.ok(successResponse)
        }
    }

    @GetMapping("/todos")
    fun getAllTodos(): ResponseEntity<SuccessResponse> {
        return todoService.getAllTodos().let {
            val messages = Messages.ALL_TODO_DETAILS.message
            val successResponse = createSuccessResponse(messages, it)
            ResponseEntity.ok(successResponse)
        }
    }

    @GetMapping("/todos/{todoId}")
    fun getTodoById(@PathVariable todoId: Int): ResponseEntity<SuccessResponse> {
        validateTodoId(todoId)

        return todoService.getById(todoId).let {
            val messages = Messages.TODO_DETAILS_BY_ID.message
            val successResponse = createSuccessResponse(messages, it)
            ResponseEntity.ok(successResponse)
        }
    }

    @GetMapping("/todos/status/{status}")
    fun getTodosByStatus(@PathVariable status: String): ResponseEntity<SuccessResponse> {
        validateStatus(status.lowercase())

        return todoService.getTodosByStatus(status.lowercase()).let {
            val messages = Messages.ALL_TODO_DETAILS.message
            val successResponse = createSuccessResponse(messages, it)
            ResponseEntity.ok(successResponse)
        }
    }

    @GetMapping("/todos/priority/{priority}")
    fun getTodosByPriority(@PathVariable priority: String): ResponseEntity<SuccessResponse> {
        validatePriority(priority.lowercase())

        return todoService.getTodosByPriority(priority.lowercase()).let {
            val messages = Messages.ALL_TODO_DETAILS.message
            val successResponse = createSuccessResponse(messages, it)
            ResponseEntity.ok(successResponse)
        }
    }

    @PutMapping("/todos/{todoId}")
    fun updateTodo(
        @PathVariable todoId: Int,
        @Valid @RequestBody updateTodoRequest: UpdateTodoRequest
    ): ResponseEntity<SuccessResponse> {
        validateTodoId(todoId)

        return todoService.updateTodo(todoId, updateTodoRequest).let {
            val messages = Messages.TODO_UPDATE_SUCCESS.message
            val successResponse = createSuccessResponse(messages, it)
            ResponseEntity.ok(successResponse)
        }
    }

    @DeleteMapping("/todos/{todoId}")
    fun deleteTodo(@PathVariable todoId: Int): ResponseEntity<SuccessResponse> {
        validateTodoId(todoId)

        return todoService.deleteTodo(todoId).let {
            val messages = Messages.TODO_DELETE_SUCCESS.message
            val successResponse = createSuccessResponse(messages, it)
            ResponseEntity.ok(successResponse)
        }
    }
}