package com.example.user.rxapp.mainScreen

import android.util.Log
import com.example.user.rxapp.data.local.LocalDataHelper
import com.example.user.rxapp.data.local.dbRoom.SimpleTaskDO
import com.example.user.rxapp.data.local.dbRoom.TaskDO
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * @author Kostiantyn Prysiazhnyi on 5/22/2018.
 */
class MainActivityPresenter @Inject constructor(private val db: LocalDataHelper) : MainActivityContract.Presenter {
    private val TAG = this.javaClass.simpleName
    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: MainActivityContract.View
    private val calendar = Calendar.getInstance()

    init {
        calendar.timeInMillis = System.currentTimeMillis()

    }

    override fun takeView(view: MainActivityContract.View) {
        this.view = view
    }

    override fun disposeAll() {
        compositeDisposable.clear()
    }

    override fun displayNewestTask() {
        Log.i(TAG, "displayNewestTask")
        compositeDisposable.add((db.getLastTask()
                .concatMap { Flowable.fromIterable(it) }
                .map {
                    it.taskDescription += "\nyou are the best"
                    it
                }
                .subscribeOn(Schedulers.computation())
                .delay(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<TaskDO>() {
                    override fun onComplete() {
                        view.showMainButton()
                        Log.i(TAG, "displayNewestTask onComplete")
                    }

                    override fun onNext(t: TaskDO?) {
                        Log.i(TAG, "displayNewestTask onNext")
                        if (t != null) {
                            view.displayTask(t)

                        }
                    }

                    override fun onError(t: Throwable?) {
                        Log.i(TAG, "displayNewestTask onError : " + t.toString())
                    }

                })))
    }

    override fun addTask(simpleTaskDO: SimpleTaskDO) {
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