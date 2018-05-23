package com.example.user.rxapp.displayTasks

import com.example.user.rxapp.data.local.dbRoom.SimpleTaskDO
import com.example.user.rxapp.data.local.dbRoom.TaskDO

/**
 * @author Kostiantyn Prysiazhnyi on 5/22/2018.
 */
interface TasksActivityContract {
    interface Presenter{
        fun addTask(simpleTaskDO : SimpleTaskDO)
        fun displayNewestTask(delay : Long)
        fun disposeAll()
        fun takeView(view : TasksActivityContract.View)
    }

    interface View{
        fun displayTask(task: TaskDO)
        fun showMainButton()
        fun sendMessageWithDelay(delayInSec : Int)
    }
}