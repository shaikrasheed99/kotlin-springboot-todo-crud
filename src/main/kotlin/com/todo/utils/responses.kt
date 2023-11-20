package com.todo.utils

import com.todo.constants.StatusResponses
import com.todo.dto.response.ErrorResponse
import com.todo.dto.response.SuccessResponse
import org.springframework.http.HttpStatus

fun createSuccessResponse(message: String, data: Any?): SuccessResponse {
    return SuccessResponse(
        status = StatusResponses.SUCCESS,
        code = HttpStatus.OK,
        message = message,
        data = data
    )
}

fun createErrorResponse(code: HttpStatus, message: String): ErrorResponse {
    return ErrorResponse(
        status = StatusResponses.ERROR,
        code = code,
        message = message,
    )
}