package com.todo.constants

enum class Messages(val message: String) {
    SERVER_IS_UP("Server is up!!"),
    TODO_CREATION_SUCCESS("Todo has created successfully"),
    TODO_DETAILS_BY_ID("Successfully fetched Todo details by id"),
    TODO_NOT_FOUND_BY_ID("Todo not found by id"),
    ALL_TODO_DETAILS("Successfully fetched all Todos details"),
    VALID_TODO_ID("Todo Id must be a valid positive number"),
    VALID_STATUS("Status should be one of: completed, pending, started"),
    VALID_PRIORITY("Priority should be one of: high, low, medium"),
}