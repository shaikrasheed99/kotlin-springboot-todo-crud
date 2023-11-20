package com.todo.constants

enum class MessageResponses(val message: String) {
    SERVER_IS_UP("Server is up!!"),
    TODO_CREATION_SUCCESS("Todo has created successfully"),
    TODO_DETAILS_BY_ID("Successfully fetched Todo details by id"),
    TODO_NOT_FOUND_BY_ID("Todo not found by id")
}