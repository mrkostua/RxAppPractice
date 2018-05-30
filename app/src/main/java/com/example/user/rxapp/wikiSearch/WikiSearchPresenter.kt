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
import java.util.*
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
                        if (t.isEmpty()) {
                            emptyTaskListMoveToMain()
                            return
                        }
                        view.initializeRecycleView(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.i(TAG, "start() onError : ${e.message}")
                        emptyTaskListMoveToMain()
                    }
                })))
    }

    override fun performSearch(taskDo: TaskDo) {
        Log.i(TAG, "performSearch()")
        val titlesList = ArrayList<String>()
        compositeDisposable.add(wikiApi.get().getContent("query", "json", "search", taskDo.taskName)
                .map {
                    if (it.query.search.isNotEmpty()) {
                        it.query.search.forEach { titlesList.add(it.title)}
                    }
                    titlesList
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<String>>() {
                    override fun onSuccess(t: List<String>) {
                        Log.i(TAG, "performSearch onSuccess")
                        view.setPBVisibility(false)
                        if (t.isEmpty()) {
                            view.showEmptyWikiListDialog("Try to change the title. No articles was found in wiki using words : ${taskDo.taskName}")
                        } else {
                            view.showDialogWithWikiTitles(t.toTypedArray())
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.i(TAG, "performSearch onError ${e.message}")
                        view.setPBVisibility(false)
                        view.showToast("Something went try again :)")
                    }

                }))
    }

    private fun emptyTaskListMoveToMain() {
        view.startMainActivity()
        view.showToast("something went wrong, try again later")
    }
}