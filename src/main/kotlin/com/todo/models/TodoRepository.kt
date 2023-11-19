package com.todo.models

import org.springframework.data.repository.CrudRepository

interface TodoRepository : CrudRepository<Todo, Int>
