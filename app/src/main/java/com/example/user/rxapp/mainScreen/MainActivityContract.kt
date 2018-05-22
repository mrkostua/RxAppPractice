package com.example.user.rxapp.mainScreen

import com.example.user.rxapp.data.local.dbRoom.SimpleTaskDO
import com.example.user.rxapp.data.local.dbRoom.TaskDO

/**
 * @author Kostiantyn Prysiazhnyi on 5/22/2018.
 */
interface MainActivityContract {
    interface Presenter{
        fun addTask(simpleTaskDO : SimpleTaskDO)
        fun displayNewestTask()
        fun disposeAll()
        fun takeView(view : MainActivityContract.View)
    }

    interface View{
        fun displayTask(task: TaskDO)
        fun showMainButton()
    }
}