package com.todo.services

import com.todo.dto.request.TodoRequest
import com.todo.exceptions.TodoNotFoundException
import com.todo.models.Todo
import com.todo.models.TodoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class TodoServiceTest {
    @Mock
    private lateinit var todoRepository: TodoRepository

    @InjectMocks
    private lateinit var todoService: TodoService

    private lateinit var todo: Todo

    @BeforeEach
    fun setUp() {
        todo = Todo(
            id = 1,
            description = "sleeping",
            status = "pending",
            priority = "high",
        )
    }

    @Test
    internal fun shouldBeAbleToCreateNewTodo() {
        `when`(todoRepository.save(any())).thenReturn(todo)
        val todoRequest = todo.let {
            TodoRequest(
                description = it.description,
                status = it.status,
                priority = it.priority
            )
        }

        val createdTodo = todoService.createTodo(todoRequest)

        assertEquals(createdTodo.id, todo.id)
        assertEquals(createdTodo.description, todo.description)
        assertEquals(createdTodo.status, todo.status)
        assertEquals(createdTodo.priority, todo.priority)
        verify(todoRepository, times(1)).save(any())
    }

    @Test
    internal fun shouldBeAbleToReturnTodoDetailsById() {
        `when`(todoRepository.findById(any(Int::class.java))).thenReturn(Optional.ofNullable(todo))

        val todoById = todo.id?.let(todoService::getById)

        assertEquals(todoById?.id, todo.id)
        assertEquals(todoById?.description, todo.description)
        assertEquals(todoById?.status, todo.status)
        assertEquals(todoById?.priority, todo.priority)
        verify(todoRepository, times(1)).findById(any(Int::class.java))
    }

    @Test
    internal fun shouldBeAbleToThrowTodoNotFoundExceptionWhenTodoIsNotPresentWithId() {
        `when`(todoRepository.findById(any(Int::class.java))).thenReturn(Optional.empty())

        assertThrows<TodoNotFoundException> {
            todo.id?.let(todoService::getById)
        }
        verify(todoRepository, times(1)).findById(any(Int::class.java))
    }

    @Test
    internal fun shouldBeAbleToReturnAllTodos() {
        val todos = listOf<Todo>(todo)
        `when`(todoRepository.findAll()).thenReturn(todos)

        val returnedTodos = todoService.getAllTodos()

        val firstTodo = returnedTodos[0]
        assertEquals(firstTodo.id, todo.id)
        assertEquals(firstTodo.description, todo.description)
        assertEquals(firstTodo.status, todo.status)
        assertEquals(firstTodo.priority, todo.priority)
        verify(todoRepository, times(1)).findAll()
    }

    @Test
    internal fun shouldBeAbleToReturnTodosByStatus() {
        val todos = mutableListOf<Todo>(todo)
        `when`(todoRepository.findByStatus(todo.status)).thenReturn(todos)

        val returnedTodos = todoService.getTodosByStatus(todo.status)

        val firstTodo = returnedTodos[0]
        assertEquals(returnedTodos.size, 1)
        assertEquals(firstTodo.id, todo.id)
        assertEquals(firstTodo.description, todo.description)
        assertEquals(firstTodo.status, todo.status)
        assertEquals(firstTodo.priority, todo.priority)
        verify(todoRepository, times(1)).findByStatus(todo.status)
    }

    @Test
    internal fun shouldBeAbleToReturnTodosByPriority() {
        val todos = mutableListOf<Todo>(todo)
        `when`(todoRepository.findByPriority(todo.priority)).thenReturn(todos)

        val returnedTodos = todoService.getTodosByPriority(todo.priority)

        val firstTodo = returnedTodos[0]
        assertEquals(returnedTodos.size, 1)
        assertEquals(firstTodo.id, todo.id)
        assertEquals(firstTodo.description, todo.description)
        assertEquals(firstTodo.status, todo.status)
        assertEquals(firstTodo.priority, todo.priority)
        verify(todoRepository, times(1)).findByPriority(todo.priority)
    }
}