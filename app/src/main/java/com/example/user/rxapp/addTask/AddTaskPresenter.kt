package com.example.user.rxapp.addTask

import com.example.user.rxapp.data.local.LocalDataHelper
import com.example.user.rxapp.data.local.dbRoom.SimpleTaskDO
import com.example.user.rxapp.data.local.dbRoom.TaskDo
import com.example.user.rxapp.tools.getNextCalendarDay
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 5/26/2018.
 */
class AddTaskPresenter @Inject constructor(private val db: LocalDataHelper) : AddTaskContract.Presenter {
    private val TAG = this.javaClass.simpleName
    private lateinit var view: AddTaskContract.View
    private val disposables = CompositeDisposable()
    override fun takeView(view: AddTaskContract.View) {
        this.view = view
    }

    override fun disposeAll() {
        disposables.clear()
    }

    override fun start() {
    }

    override fun addTask(taskToSave: TaskDo) {
        disposables.add(Completable.fromAction {
            db.addTaskToLocalDB(TaskDo(null, taskToSave.taskName, taskToSave.taskDescription,
                    taskToSave.isDone, taskToSave.deadLineDate))
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        view.showToast("Task was saved successfully")
                        view.cleanViewFromText()
                    }

                    override fun onError(e: Throwable) {
                        view.showFailedSavingTaskDialog("Some problem occurred with saving task(${taskToSave.taskName}, please try again.",
                                taskToSave)

                    }

                }))

    }

}