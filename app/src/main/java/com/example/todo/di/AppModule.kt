package com.example.todo.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.data.local.TaskDao
import com.example.todo.data.local.TaskDatabase
import com.example.todo.data.local.TaskEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun providesDatabase(application: Application): TaskDatabase {
        return Room.databaseBuilder(
            application,
            TaskDatabase::class.java,
            "task.db"
        ).build()
    }

    @Provides
    fun providesTaskDao(database: TaskDatabase) : TaskDao {
        return database.taskDao()
    }
}