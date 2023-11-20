package com.todo.services

import com.todo.dto.request.TodoRequest
import com.todo.exceptions.TodoNotFound
import com.todo.models.Todo
import com.todo.models.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TodoService(@Autowired private val todoRepository: TodoRepository) {
    fun createTodo(todoRequest: TodoRequest): Todo {
        val newTodo = Todo(
            null,
            description = todoRequest.description,
            status = todoRequest.status,
            priority = todoRequest.priority
        )

        return todoRepository.save(newTodo)
    }

    fun getById(todoId: Int): Todo {
        return todoRepository
            .findById(todoId)
            .orElseThrow { TodoNotFound("Todo not found by Id") };
    }
}
