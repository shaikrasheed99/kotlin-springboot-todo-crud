package com.todo.controllers

import com.todo.constants.Messages
import com.todo.dto.response.SuccessResponse
import com.todo.utils.createSuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping("/health")
    fun health(): ResponseEntity<SuccessResponse> {
        val messages = Messages.SERVER_IS_UP.message
        val successResponse = createSuccessResponse(messages, null)

        return ResponseEntity.ok(successResponse)
    }
}