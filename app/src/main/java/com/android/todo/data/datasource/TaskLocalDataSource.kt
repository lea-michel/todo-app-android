package com.android.todo.data.datasource

import com.android.todo.core.util.Result
import com.android.todo.data.local.LocalError
import com.android.todo.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TaskLocalDataSource {

    suspend fun insert(task: Task): Result<Unit, LocalError>

    suspend fun delete(task: Task): Result<Unit, LocalError>

    suspend fun update(task: Task): Result<Unit, LocalError>

    suspend fun updateAll(tasks: List<Task>): Result<Unit, LocalError>

    fun getAll(): Flow<Result<List<Task>, LocalError>>

    fun getById(id: String): Flow<Result<Task, LocalError>>

    fun getByCompleted(isCompleted: Boolean): Flow<Result<List<Task>, LocalError>>

    fun getByDueDate(date: LocalDate): Flow<Result<List<Task>, LocalError>>
}