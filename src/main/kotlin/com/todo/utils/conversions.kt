package com.todo.utils

import com.fasterxml.jackson.databind.ObjectMapper

fun Any.convertToJson(): String {
    return ObjectMapper().writeValueAsString(this);
}