package com.example.user.rxapp.data.local

import com.example.user.rxapp.data.local.dbRoom.TaskDo
import com.example.user.rxapp.data.local.dbRoom.TasksDB
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 5/21/2018.
 */
class LocalDataHelper @Inject constructor(private val db: TasksDB) {
    private val calendar = Calendar.getInstance()

    fun addTaskToLocalDB(task: TaskDo) {
        if ((-1).toLong() == db.tasksDao().addTask(task)) {
            throw ValueNotInsertedException("task with name : ${task.taskName} wasn't inserted")

        }
    }

    fun getAllTasksCount() = db.tasksDao().getAllTasksCount()

    fun getAllUndoneTasks() = db.tasksDao().getAllUndoneTasks()

    fun getTodayTasks(): Single<List<TaskDo>> {
        val between = getTodayDateBoundary()
        return db.tasksDao().getAllTasksForToday(between.first, between.second)
    }

    fun deleteAllTasks() {
        if (0 == db.tasksDao().deleteAllTasks() && getAllTasksCountValue() > 0) {
            throw ValueNotDeleted("there some problems with database")

        }
    }

    fun deleteAllTasksFromToday() {
        val between = getTodayDateBoundary()
        if (0 == db.tasksDao().deleteAllTasksBetween(between.first, between.second) && getAllTodayTasksCountValue() > 0) {
            throw ValueNotDeleted("there some problems with database")

        }
    }

    private fun getAllTasksCountValue() = db.tasksDao().getAllTasksCountValue()

    private fun getAllTodayTasksCountValue(): Int {
        val between = getTodayDateBoundary()
        return db.tasksDao().getAllTodayTasksCountValue(between.first, between.second)
    }

    private fun getTodayDateBoundary(): Pair<Date, Date> {
        setCalendarTime(0, 0)
        val startDate = Date(calendar.timeInMillis)
        setCalendarTime(23, 59, 59)
        val endDate = Date(calendar.timeInMillis)
        return Pair(startDate, endDate)
    }

    private fun setCalendarTime(hour: Int, min: Int, second: Int = 0) {
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = min
        calendar[Calendar.SECOND] = second
    }

}

private class ValueNotInsertedException(message: String) : Throwable(message)
private class ValueNotDeleted(message: String) : Throwable(message)