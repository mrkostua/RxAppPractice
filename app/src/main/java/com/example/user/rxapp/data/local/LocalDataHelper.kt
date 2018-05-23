package com.example.user.rxapp.data.local

import com.example.user.rxapp.data.local.dbRoom.TaskDO
import com.example.user.rxapp.data.local.dbRoom.TasksDB
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 5/21/2018.
 */
class LocalDataHelper @Inject constructor(private val db: TasksDB) {
    private val calendar = Calendar.getInstance()
    fun addTaskToLocalDB(task: TaskDO) {
        db.tasksDao().addTask(task)
    }

    fun getAllUndoneTasks() = db.tasksDao().getAllUndoneTasks()

    fun getTodayTasks(): Single<List<TaskDO>> {
        setCalendarTime(0, 0)
        val startDate = Date(calendar.timeInMillis)
        setCalendarTime(23, 59, 59)
        val endDate = Date(calendar.timeInMillis)
        return db.tasksDao().getAllTasksForToday(startDate, endDate)
    }

    private fun setCalendarTime(hour: Int, min: Int, second: Int = 0) {
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = min
        calendar[Calendar.SECOND] = second
    }
}