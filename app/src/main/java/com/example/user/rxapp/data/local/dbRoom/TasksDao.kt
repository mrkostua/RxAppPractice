package com.example.user.rxapp.data.local.dbRoom

import android.arch.persistence.room.*
import io.reactivex.Single
import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 5/21/2018.
 */
@Dao
interface TasksDao {
    @Query("SELECT COUNT(tasks.id) FROM tasks")
    fun getAllTasksCount(): Single<Int>

    @Query("SELECT COUNT(tasks.id) FROM tasks")
    fun getAllTasksCountValue(): Int

    @Query("SELECT COUNT(tasks.id) FROM tasks WHERE tasks.deadLineDate BETWEEN :fromDay AND :toDay")
    fun getAllTodayTasksCountValue(fromDay: Date, toDay: Date): Int


    @Query("SELECT * FROM tasks WHERE tasks.isDone = 0  ")
    fun getAllUndoneTasks(): Single<List<TaskDo>>

    @Query("SELECT * FROM tasks WHERE tasks.isDone = 0 AND tasks.deadLineDate BETWEEN :fromDay AND :toDay")
    fun getAllTasksForToday(fromDay: Date, toDay: Date): Single<List<TaskDo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(taskDO: TaskDo): Long

    @Query("DELETE FROM tasks WHERE tasks.deadLineDate BETWEEN :fromDay AND :toDay")
    fun deleteAllTasksBetween(fromDay: Date, toDay: Date): Int

    //UPDATE or DELETE queries can return void or int. If it is an int, the value is the number of rows affected by this query.
    @Query("DELETE FROM tasks")
    fun deleteAllTasks(): Int
}