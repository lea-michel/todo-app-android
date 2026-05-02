package com.android.todo.data.di

import com.android.todo.data.datasource.TaskLocalDataSource
import com.android.todo.data.datasource.TaskLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindTaskLocalDataSource(
        impl: TaskLocalDataSourceImpl
    ): TaskLocalDataSource
}