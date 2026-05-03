package com.android.todo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(taskEntity: TaskEntity)

    @Delete
    suspend fun delete(taskEntity: TaskEntity)

    @Update
    suspend fun update(taskEntity: TaskEntity)

    @Update
    suspend fun updateAll(tasks: List<TaskEntity>)

    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id=:id LIMIT 1")
    fun getById(id: String): Flow<TaskEntity?>

    @Query("SELECT * FROM tasks WHERE is_completed = :isCompleted")
    fun getByCompleted(isCompleted: Boolean): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE due_date = :date")
    fun getByDueDate(date: Long): Flow<List<TaskEntity>>
}