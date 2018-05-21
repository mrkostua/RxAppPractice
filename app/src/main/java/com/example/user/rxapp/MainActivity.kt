package com.example.user.rxapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.user.rxapp.data.TasksDataHelper
import com.example.user.rxapp.data.TaskDataObject
import com.example.user.rxapp.data.local.TasksDB
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    private val compositeDisposable = CompositeDisposable()
    private lateinit var db: TasksDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        db = TasksDB.getInstance(this)
        db.tasksDao().addTask(
                TaskDataObject(null, 3, 2, 5, 6, false, Date(calendar.timeInMillis))
        )


    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun bLetsStartClickListener(view: View) {
        view.visibility = View.GONE
        pbWaitForTask.visibility = View.VISIBLE

        compositeDisposable.add((db.tasksDao().getAllUndoneTasks()
                .concatMap { Flowable.fromIterable(it) }
                .map {
                    it.eatTimes += 2
                    it
                }
                .subscribeOn(Schedulers.computation())
                .delay(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<TaskDataObject>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: TaskDataObject?) {
                        if (t != null) {
                            displayTask(t)
                        }
                    }

                    override fun onError(t: Throwable?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })))
    }

    fun getSingleTestObserver(): Single<TaskDataObject>? {
        val observable = Single.just(TasksDataHelper.getCurrentDayTaskData())
        compositeDisposable.add(observable.subscribeOn(Schedulers.computation())
                .map {
                    it.eatTimes = 5
                    it
                }
                .delay(10, TimeUnit.SECONDS, Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TaskDataObject>() {
                    override fun onSuccess(t: TaskDataObject) {
                        displayTask(t)
                    }

                    override fun onError(e: Throwable) {
                    }
                }))
        return observable
    }

    private fun displayTask(task: TaskDataObject?) {
        pbWaitForTask.visibility = View.GONE
        tvMainData.visibility = View.VISIBLE
        tvMainData.text = task.toString()

    }
}
