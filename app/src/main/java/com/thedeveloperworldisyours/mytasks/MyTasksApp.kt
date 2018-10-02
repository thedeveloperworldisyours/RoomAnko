package com.thedeveloperworldisyours.mytasks

import android.app.Application
import android.arch.persistence.room.Room
import com.thedeveloperworldisyours.mytasks.database.TasksDatabase

class MyTasksApp: Application() {

    companion object {
        lateinit var database: TasksDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database =  Room.databaseBuilder(this, TasksDatabase::class.java, "tasks-db").build()
    }
}