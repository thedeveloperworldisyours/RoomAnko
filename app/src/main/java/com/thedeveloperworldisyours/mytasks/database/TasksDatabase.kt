package com.thedeveloperworldisyours.mytasks.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(TaskEntity::class), version = 2)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}