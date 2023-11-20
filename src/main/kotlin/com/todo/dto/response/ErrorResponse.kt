package com.todo.dto.response

import com.todo.constants.StatusResponses
import org.springframework.http.HttpStatus

data class ErrorResponse(
    var status: StatusResponses,
    var code: HttpStatus,
    var message: String
)
