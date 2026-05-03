package com.android.todo.data.local

import com.android.todo.core.util.RootError
import com.android.todo.domain.error.TaskError

sealed interface LocalError : RootError {
    data object DatabaseError : LocalError
    data object NotFound : LocalError
}

fun LocalError.toTaskError(): TaskError = when (this) {
    LocalError.DatabaseError -> TaskError.DatabaseError
    LocalError.NotFound -> TaskError.NotFound
}