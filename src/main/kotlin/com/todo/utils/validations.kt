package com.todo.utils

import com.todo.constants.Messages
import com.todo.exceptions.InvalidPriorityException
import com.todo.exceptions.InvalidStatusException
import com.todo.exceptions.InvalidTodoIdException

fun validateTodoId(id: Int) {
    if (id <= 0) {
        throw InvalidTodoIdException(Messages.VALID_TODO_ID.message + " - $id")
    }
}

fun validateStatus(status: String) {
    val statuses = setOf<String>("completed", "started", "pending")

    if (!statuses.contains(status)) {
        throw InvalidStatusException(Messages.VALID_STATUS.message + " - $status")
    }
}

fun validatePriority(priority: String) {
    val priorities = setOf<String>("high", "low", "medium")

    if (!priorities.contains(priority)) {
        throw InvalidPriorityException(Messages.VALID_PRIORITY.message + " - $priority")
    }
}