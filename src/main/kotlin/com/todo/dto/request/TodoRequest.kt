package com.todo.dto.request

data class TodoRequest(
    val description: String,
    val status: String,
    val priority: String
)
