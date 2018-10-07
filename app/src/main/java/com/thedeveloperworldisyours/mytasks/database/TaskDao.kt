package com.thedeveloperworldisyours.mytasks.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
interface TaskDao {

    @Query("select * from task")
    fun getAllTasks(): MutableList<Task>

    @Query("select * from task where id = :id")
    fun findTaskById(id: Long): Task

    @Insert(onConflict = REPLACE)
    fun insertTask(task: Task):Long

    @Update(onConflict = REPLACE)
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)


}