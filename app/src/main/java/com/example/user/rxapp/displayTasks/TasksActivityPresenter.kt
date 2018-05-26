package com.example.user.rxapp.displayTasks

import android.util.Log
import com.example.user.rxapp.data.local.LocalDataHelper
import com.example.user.rxapp.data.local.dbRoom.SimpleTaskDO
import com.example.user.rxapp.data.local.dbRoom.TaskDo
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
import kotlin.collections.ArrayList


/**
 * @author Kostiantyn Prysiazhnyi on 5/22/2018.
 */
class TasksActivityPresenter @Inject constructor(private val db: LocalDataHelper) : TasksActivityContract.Presenter {
    private val TAG = this.javaClass.simpleName
    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: TasksActivityContract.View

    override fun takeView(view: TasksActivityContract.View) {
        this.view = view
    }

    override fun disposeAll() {
        compositeDisposable.clear()
    }

    override fun displayNewestTask(delay: Long) {
        var isFirst = true
        val emptyTask = TaskDo(null, "", "", false, Date(Calendar.getInstance().timeInMillis))
        compositeDisposable.add((db.getAllUndoneTasks()
                .flattenAsObservable {
                    (it as ArrayList).add(emptyTask)
                    it
                }
                .concatMap {
                    if (isFirst) {
                        isFirst = false
                        Observable.just(it)
                    } else {
                        Observable.just(it).delay(delay, TimeUnit.SECONDS)
                    }
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    for (i in 1..delay.toInt()) {
                        view.sendMessageWithDelay(i)
                        Log.i(TAG, "displayNewestTask doOnNext ${it.id} + $i")
                    }
                }
                .subscribeWith(object : DisposableObserver<TaskDo>() {

                    override fun onNext(t: TaskDo) {
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

}