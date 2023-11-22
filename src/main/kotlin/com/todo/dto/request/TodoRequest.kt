package com.todo.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class TodoRequest(
    @field:NotBlank(message = "Requested Description cannot be blank")
    val description: String,

    @field:Pattern(
        regexp = "^(?i)(completed|pending|started)$",
        message = "Requested Status should be one of: completed, pending, started"
    )
    val status: String,

    @field:Pattern(
        regexp = "^(?i)(high|low|medium)$",
        message = "Requested Priority should be one of: high, low, medium"
    )
    val priority: String
)
