package com.todo.services

import com.todo.dto.request.TodoRequest
import com.todo.models.Todo
import com.todo.models.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TodoService(@Autowired private val todoRepository: TodoRepository) {
    fun createTodo(todoRequest: TodoRequest): Todo {
        val newTodo = Todo(
            null,
            todoRequest.description,
            todoRequest.status,
            todoRequest.priority
        )

        return todoRepository.save(newTodo)
    }
}
