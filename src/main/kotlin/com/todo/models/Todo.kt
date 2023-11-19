package com.todo.models

import jakarta.persistence.*

@Entity
@Table(name = "todos")
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var description: String,
    var status: String,
    var priority: String
)
