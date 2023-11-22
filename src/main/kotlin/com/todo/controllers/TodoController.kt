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
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TodoController(private val todoService: TodoService) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/todos")
    fun addTodo(@Valid @RequestBody todoRequest: TodoRequest): ResponseEntity<SuccessResponse> {
        logger.info("Initializing add todo API")

        return todoService.createTodo(todoRequest).let {
            val message = Messages.TODO_CREATION_SUCCESS.message
            val successResponse = createSuccessResponse(message, it)
            logger.info(message)
            ResponseEntity.ok(successResponse)
        }
    }

    @GetMapping("/todos")
    fun getAllTodos(): ResponseEntity<SuccessResponse> {
        logger.info("Initializing get all todos API")

        return todoService.getAllTodos().let {
            val message = Messages.ALL_TODO_DETAILS.message
            val successResponse = createSuccessResponse(message, it)
            logger.info(message)
            ResponseEntity.ok(successResponse)
        }
    }

    @GetMapping("/todos/{todoId}")
    fun getTodoById(@PathVariable todoId: Int): ResponseEntity<SuccessResponse> {
        logger.info("Initializing get todo API of id - $todoId")
        validateTodoId(todoId)

        return todoService.getById(todoId).let {
            val message = Messages.TODO_DETAILS_BY_ID.message
            val successResponse = createSuccessResponse(message, it)
            logger.info(message)
            ResponseEntity.ok(successResponse)
        }
    }

    @GetMapping("/todos/status/{status}")
    fun getTodosByStatus(@PathVariable status: String): ResponseEntity<SuccessResponse> {
        logger.info("Initializing get todos API by status - $status")
        validateStatus(status.lowercase())

        return todoService.getTodosByStatus(status.lowercase()).let {
            val message = Messages.ALL_TODO_DETAILS.message
            val successResponse = createSuccessResponse(message, it)
            logger.info(message)
            ResponseEntity.ok(successResponse)
        }
    }

    @GetMapping("/todos/priority/{priority}")
    fun getTodosByPriority(@PathVariable priority: String): ResponseEntity<SuccessResponse> {
        logger.info("Initializing get todos API by priority - $priority")
        validatePriority(priority.lowercase())

        return todoService.getTodosByPriority(priority.lowercase()).let {
            val message = Messages.ALL_TODO_DETAILS.message
            val successResponse = createSuccessResponse(message, it)
            logger.info(message)
            ResponseEntity.ok(successResponse)
        }
    }

    @PutMapping("/todos/{todoId}")
    fun updateTodo(
        @PathVariable todoId: Int,
        @Valid @RequestBody updateTodoRequest: UpdateTodoRequest
    ): ResponseEntity<SuccessResponse> {
        logger.info("Initializing update todo API by id - $todoId")
        validateTodoId(todoId)

        return todoService.updateTodo(todoId, updateTodoRequest).let {
            val message = Messages.TODO_UPDATE_SUCCESS.message
            val successResponse = createSuccessResponse(message, it)
            logger.info(message)
            ResponseEntity.ok(successResponse)
        }
    }

    @DeleteMapping("/todos/{todoId}")
    fun deleteTodo(@PathVariable todoId: Int): ResponseEntity<SuccessResponse> {
        logger.info("Initializing delete todo API by id - $todoId")
        validateTodoId(todoId)

        return todoService.deleteTodo(todoId).let {
            val message = Messages.TODO_DELETE_SUCCESS.message
            val successResponse = createSuccessResponse(message, it)
            logger.info(message)
            ResponseEntity.ok(successResponse)
        }
    }
}