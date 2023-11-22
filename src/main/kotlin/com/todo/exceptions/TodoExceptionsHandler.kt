package com.todo.exceptions

import com.todo.dto.response.ErrorResponse
import com.todo.utils.createErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class TodoExceptionsHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(TodoNotFoundException::class)
    fun handleTodoNotFoundException(todoNotFoundException: TodoNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = todoNotFoundException.message?.let {
            logger.error(it)
            createErrorResponse(HttpStatus.NOT_FOUND, it)
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(
        value = [
            MethodArgumentNotValidException::class,
            MethodArgumentTypeMismatchException::class
        ]
    )
    fun handleMethodArgumentNotValidAndMismatchException(exception: Exception): ResponseEntity<HashMap<String, String?>> {
        val errors = HashMap<String, String?>()

        when (exception) {
            is MethodArgumentNotValidException -> {
                val fieldErrors = exception.fieldErrors

                fieldErrors.forEach {
                    errors[it.field] = it.defaultMessage
                }
            }

            is MethodArgumentTypeMismatchException -> {
                errors[exception.name] = exception.message
            }
        }
        logger.error(errors.toString())

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
    }

    @ExceptionHandler(
        value = [
            InvalidStatusException::class,
            InvalidPriorityException::class,
            InvalidTodoIdException::class
        ]
    )
    fun handleInvalidStatusAndPriorityException(exception: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = exception.message?.let {
            logger.error(it)
            createErrorResponse(HttpStatus.BAD_REQUEST, it)
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}