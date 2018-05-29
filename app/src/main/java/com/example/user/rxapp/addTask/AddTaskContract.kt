package com.example.user.rxapp.addTask

import com.example.user.rxapp.data.local.dbRoom.SimpleTaskDO
import com.example.user.rxapp.data.local.dbRoom.TaskDo
import com.example.user.rxapp.tools.BasePresenter
import com.example.user.rxapp.tools.BaseView

/**
 * @author Kostiantyn Prysiazhnyi on 5/26/2018.
 */
interface AddTaskContract {
    interface View : BaseView<Presenter> {
        fun showFailedSavingTaskDialog(message: String, failedTask: TaskDo)
        fun cleanViewFromText()

    }

    interface Presenter : BasePresenter<View> {
        fun addTask(taskToSave : TaskDo)

    }
}