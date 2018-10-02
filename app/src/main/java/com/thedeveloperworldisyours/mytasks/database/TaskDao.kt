package com.thedeveloperworldisyours.mytasks.database

import android.arch.persistence.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_entity")
    fun getAllTasks(): MutableList<TaskEntity>

    @Insert
    fun addTask(taskEntity : TaskEntity):Long

    @Query("SELECT * FROM task_entity where id like :arg0")
    fun getTaskById(id: Long): TaskEntity

    @Delete
    fun deleteTask(taskEntity: TaskEntity):Int

    @Update
    fun updateTask(taskEntity: TaskEntity):Int

}