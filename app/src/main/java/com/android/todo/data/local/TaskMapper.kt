package com.android.todo.data.local

import com.android.todo.domain.model.Task
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun TaskEntity.toTask() = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    isCompleted = this.isCompleted,
    createdAt = Instant.fromEpochMilliseconds(this.createdAt)
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    dueDate = this.dueDate?.let {
        Instant.fromEpochMilliseconds(it)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date
    },
    categoryId = this.categoryId,
    position = this.position
)

fun Task.toTaskEntity() = TaskEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    isCompleted = this.isCompleted,
    createdAt = this.createdAt.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
    dueDate = this.dueDate?.atStartOfDayIn(TimeZone.currentSystemDefault())?.toEpochMilliseconds(),
    categoryId = this.categoryId,
    position = this.position
)