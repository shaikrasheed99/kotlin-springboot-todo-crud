package com.todo.exceptions

import com.todo.constants.Messages
import com.todo.dto.response.ErrorResponse
import com.todo.utils.createErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class TodoExceptionsHandler {
    @ExceptionHandler(TodoNotFoundException::class)
    fun handleTodoNotFoundException(todoNotFoundException: TodoNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse =
            createErrorResponse(HttpStatus.NOT_FOUND, Messages.TODO_NOT_FOUND_BY_ID.message)

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

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
    }

    @ExceptionHandler(
        value = [
            InvalidStatusException::class,
            InvalidPriorityException::class,
            InvalidIdException::class
        ]
    )
    fun handleInvalidStatusAndPriorityException(exception: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = exception.message?.let {
            createErrorResponse(HttpStatus.BAD_REQUEST, it)
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}