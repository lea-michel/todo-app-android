package com.android.todo.domain.error

import com.android.todo.core.util.RootError

interface TaskError : RootError {
    data object DatabaseError : TaskError
    data object NotFound : TaskError
    data object TitleTooLong : TaskError
    data object DescriptionTooLong : TaskError
}