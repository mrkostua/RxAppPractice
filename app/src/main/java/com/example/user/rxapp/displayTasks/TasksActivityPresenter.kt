package com.example.user.rxapp.displayTasks

import android.util.Log
import com.example.user.rxapp.data.local.LocalDataHelper
import com.example.user.rxapp.data.local.dbRoom.SimpleTaskDO
import com.example.user.rxapp.data.local.dbRoom.TaskDO
import com.example.user.rxapp.tools.getNextCalendarDay
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * @author Kostiantyn Prysiazhnyi on 5/22/2018.
 */
class TasksActivityPresenter @Inject constructor(private val db: LocalDataHelper) : TasksActivityContract.Presenter {
    private val TAG = this.javaClass.simpleName
    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: TasksActivityContract.View
    private val calendar = Calendar.getInstance()

    init {
        calendar.timeInMillis = System.currentTimeMillis()

    }

    override fun takeView(view: TasksActivityContract.View) {
        this.view = view
    }

    override fun disposeAll() {
        compositeDisposable.clear()
    }

    override fun displayNewestTask(delay: Long) {
        compositeDisposable.add((db.getAllUndoneTasks()
                .flattenAsObservable { it }
                .concatMap {
                    Observable.just(it).delay(delay, TimeUnit.SECONDS)
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TaskDO>() {

                    override fun onNext(t: TaskDO) {
                        Log.i(TAG, "displayNewestTask onNext Yo yo ${t.id}")
                        view.displayTask(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.i(TAG, "displayNewestTask onError : " + e.toString())
                    }

                    override fun onComplete() {
                        view.showMainButton()
                        Log.i(TAG, "displayNewestTask onComplete")
                    }

                })))
    }

    override fun addTask(simpleTaskDO: SimpleTaskDO) {
        Log.i(TAG, "addT")
        calendar[Calendar.DAY_OF_WEEK] = getNextCalendarDay(calendar)
        compositeDisposable.add(Completable.fromAction {
            db.addTaskToLocalDB(TaskDO(null, simpleTaskDO.taskName, simpleTaskDO.taskDescription,
                    simpleTaskDO.isDone, Date(calendar.timeInMillis)))
        }
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                    }

                }))

    }
}