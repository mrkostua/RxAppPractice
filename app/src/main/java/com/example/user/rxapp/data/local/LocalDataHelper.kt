package com.example.user.rxapp.data.local

import com.example.user.rxapp.data.local.dbRoom.TaskDO
import com.example.user.rxapp.data.local.dbRoom.TasksDB
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 5/21/2018.
 */
class LocalDataHelper @Inject constructor(private val db: TasksDB) {
    fun addTaskToLocalDB(task: TaskDO) {
        db.tasksDao().addTask(task)
    }

    fun getLastTask() = db.tasksDao().getAllUndoneTasks()
}