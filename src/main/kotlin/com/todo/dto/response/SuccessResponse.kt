package com.todo.dto.response

import com.todo.constants.StatusResponses
import org.springframework.http.HttpStatus

data class SuccessResponse(
    val status: StatusResponses,
    val code: HttpStatus,
    val message: String,
    val data: Any?
)