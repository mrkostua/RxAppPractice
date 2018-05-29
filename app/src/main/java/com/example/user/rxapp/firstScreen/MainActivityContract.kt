package com.example.user.rxapp.firstScreen

/**
 * @author Kostiantyn Prysiazhnyi on 5/24/2018.
 */
interface MainActivityContract {
    interface View {
        fun showToast(msg: String)
        fun showFailedDialog(message: String)
        fun startTaskActivity()
        fun startWikiSearchActivity()
        fun setPBVisibility(visible: Boolean)
    }

    interface Presenter {
        fun start()
        fun takeView(view: MainActivityContract.View)
        fun deleteAllTasks()
        fun deleteTodayTasks()
        fun disposeAll()
        fun startActivity(activityName: String)
    }
}