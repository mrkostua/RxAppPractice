package com.example.user.rxapp.wikiSearch

import android.util.Log
import com.example.user.rxapp.data.local.LocalDataHelper
import com.example.user.rxapp.data.local.dbRoom.TaskDo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 5/29/2018.
 */
class WikiSearchPresenter @Inject constructor(private val db: LocalDataHelper) : WikiSearchContract.Presenter {
    private val TAG = this.javaClass.simpleName
    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: WikiSearchContract.View

    override fun takeView(view: WikiSearchContract.View) {
        this.view = view
    }

    override fun disposeAll() {
        compositeDisposable.clear()
    }

    override fun start() {
        compositeDisposable.add((db.getAllUndoneTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<TaskDo>>() {
                    override fun onSuccess(t: List<TaskDo>) {
                        view.initializeRecycleView(t)

                    }

                    override fun onError(e: Throwable) {
                        Log.i(TAG, "start() onError : ${e.message}")
                        view.startMainActivity()
                        view.showToast("something went wrong, try again later")
                    }
                })))
    }

    override fun performSearch(taskDo: TaskDo) {
        //TODO perform http request with retrofit to Wiki API
    }
}