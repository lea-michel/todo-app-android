package com.android.todo.domain.repository

import com.android.todo.core.util.Result
import com.android.todo.domain.error.TaskError
import com.android.todo.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<Result<List<Task>, TaskError>>
    fun getTaskById(id: String): Flow<Result<Task, TaskError>>
    suspend fun insertTask(task: Task): Result<Unit, TaskError>
    suspend fun updateTask(task: Task): Result<Unit, TaskError>
    suspend fun deleteTask(task: Task): Result<Unit, TaskError>
}