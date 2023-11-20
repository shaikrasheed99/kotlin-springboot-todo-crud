package com.todo.utils

import com.todo.exceptions.InvalidIdException
import com.todo.exceptions.InvalidPriorityException
import com.todo.exceptions.InvalidStatusException

fun validateTodoId(id: Int) {
    if (id <= 0) {
        throw InvalidIdException("Todo Id must be a valid positive number")
    }
}

fun validateStatus(status: String) {
    val statuses = setOf<String>("completed", "started", "pending")

    if (!statuses.contains(status)) {
        throw InvalidStatusException("Status should be one of: completed, pending, started")
    }
}

fun validatePriority(priority: String) {
    val priorities = setOf<String>("high", "low", "medium")

    if (!priorities.contains(priority)) {
        throw InvalidPriorityException("Priority should be one of: high, low, medium")
    }
}