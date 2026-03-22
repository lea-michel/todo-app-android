package com.android.todo.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class Task(
    val id: String,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val createdAt: LocalDateTime,
    val dueDate: LocalDate?,
    val categoryId: String?,
    val position: Int
)