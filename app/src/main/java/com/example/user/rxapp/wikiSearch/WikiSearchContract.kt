package com.example.user.rxapp.wikiSearch

import com.example.user.rxapp.data.local.dbRoom.TaskDo
import com.example.user.rxapp.tools.BasePresenter
import com.example.user.rxapp.tools.BaseView

interface WikiSearchContract {
    interface Presenter : BasePresenter<View> {
        fun performSearch(taskDo: TaskDo)
    }

    interface View : BaseView<Presenter> {
        fun initializeRecycleView(data: List<TaskDo>)
        fun startMainActivity()
    }

}
