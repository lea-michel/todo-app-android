package com.android.todo.data.repository

import com.android.todo.core.util.Result
import com.android.todo.data.datasource.TaskLocalDataSource
import com.android.todo.data.local.toTaskError

import com.android.todo.domain.error.TaskError
import com.android.todo.domain.model.Task
import com.android.todo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl
@Inject
constructor(
    private val taskLocalDataSource: TaskLocalDataSource
) : TaskRepository {
    override fun getAllTasks(): Flow<Result<List<Task>, TaskError>> {
        return taskLocalDataSource.getAll().map { result ->
            when (result) {
                is Result.Success -> result
                is Result.Error -> Result.Error(result.error.toTaskError())
            }
        }
    }

    override fun getTaskById(id: String): Flow<Result<Task, TaskError>> {
        return taskLocalDataSource.getById(id).map { result ->
            when (result) {
                is Result.Success -> result
                is Result.Error -> Result.Error(result.error.toTaskError())
            }
        }
    }

    override suspend fun insertTask(task: Task): Result<Unit, TaskError> {
        return when (val res = taskLocalDataSource.insert(task)) {
            is Result.Error -> Result.Error(res.error.toTaskError())
            is Result.Success -> res
        }
    }

    override suspend fun updateTask(task: Task): Result<Unit, TaskError> {
        return when (val res = taskLocalDataSource.update(task)) {
            is Result.Error -> Result.Error(res.error.toTaskError())
            is Result.Success -> res
        }
    }

    override suspend fun deleteTask(task: Task): Result<Unit, TaskError> {
        return when (val res = taskLocalDataSource.delete(task)) {
            is Result.Error -> Result.Error(res.error.toTaskError())
            is Result.Success -> res
        }
    }
}