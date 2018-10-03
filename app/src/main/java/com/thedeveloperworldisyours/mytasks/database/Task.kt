package com.thedeveloperworldisyours.mytasks.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by javiergonzalezcabezas on 3/10/18.
 */
@Entity(tableName = "task")
data class Task(@ColumnInfo(name = "completed_flag") var completed: Boolean = false,
                @ColumnInfo(name = "task_desciption") var description: String) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}