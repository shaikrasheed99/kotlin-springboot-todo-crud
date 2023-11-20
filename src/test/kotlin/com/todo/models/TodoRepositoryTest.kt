package com.todo.models

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class TodoRepositoryTest {
    @Autowired
    private lateinit var todoRepository: TodoRepository

    private lateinit var todo: Todo

    @BeforeEach
    fun setUp() {
        todo = Todo(
            id = 1,
            description = "sleeping",
            status = "completed",
            priority = "high"
        ).also(todoRepository::save)
    }

    @AfterEach
    fun tearDown() {
        todoRepository.deleteAll()
    }

    @Test
    internal fun shouldBeAbleToReturnTodosByStatus() {
        val todos = todoRepository.findByStatus(todo.status)

        val firstTodo = todos[0]
        assertEquals(todos.size, 1)
        assertEquals(firstTodo.id, todo.id)
        assertEquals(firstTodo.description, todo.description)
        assertEquals(firstTodo.status, todo.status)
        assertEquals(firstTodo.priority, todo.priority)
    }

    @Test
    internal fun shouldBeAbleToReturnEmptyTodosListWhenStatusIsNotPresent() {
        val todos = todoRepository.findByStatus("pending")

        assertEquals(todos.size, 0)
    }

    @Test
    internal fun shouldBeAbleToReturnTodosByPriority() {
        val todos = todoRepository.findByPriority(todo.priority)

        val firstTodo = todos[0]
        assertEquals(todos.size, 1)
        assertEquals(firstTodo.id, todo.id)
        assertEquals(firstTodo.description, todo.description)
        assertEquals(firstTodo.status, todo.status)
        assertEquals(firstTodo.priority, todo.priority)
    }

    @Test
    internal fun shouldBeAbleToReturnEmptyTodosListWhenPriorityIsNotPresent() {
        val todos = todoRepository.findByPriority("low")

        assertEquals(todos.size, 0)
    }
}