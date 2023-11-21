package com.todo.dto.request

import jakarta.validation.constraints.Pattern

data class UpdateTodoRequest(
    val description: String?,

    @field:Pattern(
        regexp = "^(?i)(completed|pending|started)$",
        message = "Status should be one of: completed, pending, started"
    )
    val status: String?,

    @field:Pattern(
        regexp = "^(?i)(high|low|medium)$",
        message = "Priority should be one of: high, low, medium"
    )
    val priority: String?
)