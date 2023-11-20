package com.todo.utils

import com.todo.constants.Messages
import com.todo.exceptions.InvalidIdException
import com.todo.exceptions.InvalidPriorityException
import com.todo.exceptions.InvalidStatusException

fun validateTodoId(id: Int) {
    if (id <= 0) {
        throw InvalidIdException(Messages.VALID_TODO_ID.message)
    }
}

fun validateStatus(status: String) {
    val statuses = setOf<String>("completed", "started", "pending")

    if (!statuses.contains(status)) {
        throw InvalidStatusException(Messages.VALID_STATUS.message)
    }
}

fun validatePriority(priority: String) {
    val priorities = setOf<String>("high", "low", "medium")

    if (!priorities.contains(priority)) {
        throw InvalidPriorityException(Messages.VALID_PRIORITY.message)
    }
}