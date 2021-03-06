package com.thedeveloperworldisyours.mytasks

import android.app.Application
import android.arch.persistence.room.Room
import com.thedeveloperworldisyours.mytasks.database.AppDatabase

class MyTasksApp: Application() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database =  Room.databaseBuilder(this, AppDatabase::class.java, "tasks-db").build()
    }
}