package com.example.user.rxapp.displayMain

import com.example.user.rxapp.data.local.dbRoom.SimpleTaskDO

/**
 * @author Kostiantyn Prysiazhnyi on 5/24/2018.
 */
interface MainActivityContract {
    interface View {
        fun showToast(msg : String)
        fun showFailedDialog(message: String)
        fun startTaskActivity()
        fun setPBVisibility(visible: Boolean)
        fun showFailedSavingTaskDialog(message: String, failedTask: SimpleTaskDO)
    }

    interface Presenter {
        fun start()
        fun takeView(view: MainActivityContract.View)
        fun deleteAllTasks()
        fun deleteTodayTasks()
        fun disposeAll()
        fun startTaskActivity()
        fun addTask(simpleTaskDO: SimpleTaskDO)
    }
}