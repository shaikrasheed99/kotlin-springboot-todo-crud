package com.todo.models

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface TodoRepository : CrudRepository<Todo, Int> {
    @Query(value = "SELECT * FROM todos WHERE status = :status", nativeQuery = true)
    fun findByStatus(status: String): MutableList<Todo>
}
