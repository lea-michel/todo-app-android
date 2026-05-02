package com.android.todo.data.datasource

import com.android.todo.core.util.Result
import com.android.todo.data.local.LocalError
import com.android.todo.data.local.TaskDao
import com.android.todo.data.local.TaskEntity
import com.android.todo.data.local.toTask
import com.android.todo.data.local.toTaskEntity
import com.android.todo.domain.model.Task
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class TaskLocalDataSourceImpl
@Inject
constructor(
    private val taskDao: TaskDao
) : TaskLocalDataSource {
    override suspend fun insert(task: Task): Result<Unit, LocalError> {
        return try {
            taskDao.insert(taskEntity = task.toTaskEntity())
            Result.Success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.Error(LocalError.DatabaseError)
        }
    }

    override suspend fun delete(task: Task): Result<Unit, LocalError> {
        return try {
            taskDao.delete(task.toTaskEntity())
            Result.Success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.Error(LocalError.DatabaseError)
        }
    }

    override suspend fun update(task: Task): Result<Unit, LocalError> {
        return try {
            taskDao.update(task.toTaskEntity())
            Result.Success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.Error(LocalError.DatabaseError)
        }
    }

    override suspend fun updateAll(tasks: List<Task>): Result<Unit, LocalError> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<Result<List<Task>, LocalError>> {
        return taskDao.getAll()
            .map<List<TaskEntity>, Result<List<Task>, LocalError>> { tasks ->
                Result.Success(tasks.map { it.toTask() })
            }
            .catch { e ->
                emit(Result.Error(LocalError.DatabaseError))
            }
    }

    override fun getById(id: String): Flow<Result<Task, LocalError>> {
        return taskDao.getById(id)
            .map<TaskEntity?, Result<Task, LocalError>> { task ->
                task?.let { Result.Success(it.toTask()) } ?: Result.Error(LocalError.NotFound)
            }
            .catch { e ->
                emit(Result.Error(LocalError.DatabaseError))
            }
    }

    override fun getByCompleted(isCompleted: Boolean): Flow<Result<List<Task>, LocalError>> {
        TODO("Not yet implemented")
    }

    override fun getByDueDate(date: LocalDate): Flow<Result<List<Task>, LocalError>> {
        TODO("Not yet implemented")
    }

}