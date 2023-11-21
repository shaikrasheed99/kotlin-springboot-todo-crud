package com.todo.services

import com.todo.constants.Messages
import com.todo.dto.request.TodoRequest
import com.todo.dto.request.UpdateTodoRequest
import com.todo.exceptions.TodoNotFoundException
import com.todo.models.Todo
import com.todo.models.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.StreamSupport

@Service
class TodoService(@Autowired private val todoRepository: TodoRepository) {
    fun createTodo(todoRequest: TodoRequest): Todo {
        val newTodo = Todo(
            null,
            description = todoRequest.description,
            status = todoRequest.status.lowercase(),
            priority = todoRequest.priority.lowercase()
        )

        return todoRepository.save(newTodo)
    }

    fun getById(todoId: Int): Todo {
        return todoRepository
            .findById(todoId)
            .orElseThrow { TodoNotFoundException(Messages.TODO_NOT_FOUND_BY_ID.message) };
    }

    fun getAllTodos(): MutableList<Todo> {
        return StreamSupport
            .stream(todoRepository.findAll().spliterator(), false)
            .toList()
    }

    fun getTodosByStatus(status: String): MutableList<Todo> {
        return todoRepository.findByStatus(status)
    }

    fun getTodosByPriority(priority: String): MutableList<Todo> {
        return todoRepository.findByPriority(priority)
    }

    fun updateTodo(todoId: Int, updateTodoRequest: UpdateTodoRequest): Todo {
        val todo = getById(todoId).apply {
            description = updateTodoRequest.description ?: description
            status = updateTodoRequest.status?.lowercase() ?: status
            priority = updateTodoRequest.priority?.lowercase() ?: priority
        }

        return todoRepository.save(todo)
    }

    fun deleteTodo(todoId: Int) {
        getById(todoId).let {
            todoRepository.delete(it)
        }
    }
}
