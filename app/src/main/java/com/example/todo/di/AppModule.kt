package com.example.todo.di

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.data.local.TaskDao
import com.example.todo.data.local.TaskDatabase
import com.example.todo.data.local.TaskEntity
import com.example.todo.utils.alarmManager.TaskAlarmScheduler
import com.example.todo.utils.alarmManager.TaskNotification
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Singleton
    @Provides
    fun providesTaskAlarmScheduler(@ApplicationContext context: Context): TaskAlarmScheduler {
        return TaskAlarmScheduler(context)
    }

    @Singleton
    @Provides
    fun providesNotificationManager(@ApplicationContext context: Context): TaskNotification {
        return TaskNotification(context)
    }
}