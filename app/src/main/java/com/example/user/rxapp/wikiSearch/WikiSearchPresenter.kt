package com.example.user.rxapp.wikiSearch

import android.util.Log
import com.example.user.rxapp.data.local.LocalDataHelper
import com.example.user.rxapp.data.local.dbRoom.TaskDo
import com.example.user.rxapp.wikiSearch.wikiAPI.WikiApiService
import com.example.user.rxapp.wikiSearch.wikiAPI.WikiSearchModel
import com.google.gson.Gson
import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 5/29/2018.
 */
class WikiSearchPresenter @Inject constructor(private val db: LocalDataHelper, private val wikiApi: Lazy<WikiApiService>) : WikiSearchContract.Presenter {
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
        Log.i(TAG,"performSearch()")
        compositeDisposable.add(wikiApi.get().getContent("query","json","search","Java")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WikiSearchModel.Result>() {
                    override fun onSuccess(t: WikiSearchModel.Result) {
                        Log.i(TAG, "performSearch " + t.query.search[2].toString())
                        view.setPBVisibility(false)
                    }

                    override fun onError(e: Throwable) {
                        Log.i(TAG, "performSearch onError ${e.message}")
                        view.setPBVisibility(false)
                    }

                }))
    }
}