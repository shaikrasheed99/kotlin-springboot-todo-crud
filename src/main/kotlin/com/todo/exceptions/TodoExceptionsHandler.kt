package com.todo.exceptions

import com.todo.constants.MessageResponses
import com.todo.dto.response.ErrorResponse
import com.todo.utils.createErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class TodoExceptionsHandler {
    @ExceptionHandler(TodoNotFound::class)
    fun handleTodoNotFoundException(todoNotFound: TodoNotFound): ResponseEntity<ErrorResponse> {
        val errorResponse =
            createErrorResponse(HttpStatus.NOT_FOUND, MessageResponses.TODO_NOT_FOUND_BY_ID.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        methodArgumentNotValidException: MethodArgumentNotValidException
    ): ResponseEntity<HashMap<String, String?>> {
        val errors = HashMap<String, String?>()
        val fieldErrors = methodArgumentNotValidException.fieldErrors

        fieldErrors.forEach {
            errors[it.field] = it.defaultMessage
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
    }
}