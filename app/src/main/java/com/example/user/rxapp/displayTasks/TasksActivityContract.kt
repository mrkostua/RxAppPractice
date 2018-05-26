package com.example.user.rxapp.displayTasks

import com.example.user.rxapp.data.local.dbRoom.TaskDo

/**
 * @author Kostiantyn Prysiazhnyi on 5/22/2018.
 */
interface TasksActivityContract {
    interface Presenter{
        fun displayNewestTask(delay : Long)
        fun disposeAll()
        fun takeView(view : TasksActivityContract.View)
    }

    interface View{
        fun displayTask(task: TaskDo)
        fun showMainButton()
        fun sendMessageWithDelay(delayInSec : Int)
        fun showToast(sms : String)
    }
}