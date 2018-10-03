package com.thedeveloperworldisyours.mytasks.database

/**
 * Created by javiergonzalezcabezas on 3/10/18.
 */

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(Task::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}