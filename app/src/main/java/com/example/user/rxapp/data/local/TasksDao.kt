package com.example.user.rxapp.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.user.rxapp.data.TaskDataObject
import io.reactivex.Flowable
import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 5/21/2018.
 */
@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks WHERE tasks.isDone = 1")
    fun getAllUndoneTasks(): Flowable<List<TaskDataObject>>

    @Query("SELECT * FROM tasks WHERE tasks.isDone = 1 AND tasks.date BETWEEN :fromDay AND :toDay")
    fun getAllTasksForToday(fromDay: Date, toDay: Date): Flowable<List<TaskDataObject>>

    @Insert()
    fun addTask(taskDataObject: TaskDataObject)
}