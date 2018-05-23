package com.example.user.rxapp.data.local.dbRoom

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single
import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 5/21/2018.
 */
@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks WHERE tasks.isDone = 0  ")
    fun getAllUndoneTasks(): Single<List<TaskDO>>

    @Query("SELECT * FROM tasks WHERE tasks.isDone = 1 AND tasks.deadLineDate BETWEEN :fromDay AND :toDay")
    fun getAllTasksForToday(fromDay: Date, toDay: Date): Single<List<TaskDO>>

    @Insert()
    fun addTask(taskDO: TaskDO)
}