package com.example.user.rxapp.wikiSearch

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.user.rxapp.R
import com.example.user.rxapp.data.local.dbRoom.TaskDo
import com.example.user.rxapp.firstScreen.MainActivity
import com.example.user.rxapp.tools.DisplayingHelper
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_wiki_search.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 5/26/2018.
 */
class WikiSearchActivity : DaggerAppCompatActivity(), WikiSearchContract.View {
    @Inject
    public lateinit var presenter: WikiSearchContract.Presenter

    private lateinit var tasksRecycleViewAdapter: TasksRecycleViewAdapter
    private lateinit var rvSearchButtonDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wiki_search)
        presenter.takeView(this)
        presenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeAll()
        rvSearchButtonDisposable.dispose()
    }

    override fun showToast(sms: String) {
        DisplayingHelper.showToast(this, sms)

    }


    override fun initializeRecycleView(data: List<TaskDo>) {
        setPBVisibility(false)
        rvListOfAllTasks.visibility = View.VISIBLE
        tasksRecycleViewAdapter = TasksRecycleViewAdapter(data)
        rvListOfAllTasks.layoutManager = LinearLayoutManager(this)
        rvListOfAllTasks.adapter = tasksRecycleViewAdapter
        rvListOfAllTasks.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        rvSearchButtonDisposable = tasksRecycleViewAdapter.getSearchWikiButtonObservable().subscribe {
            setPBVisibility(true)
            presenter.performSearch(it)
        }
    }

    override fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun setPBVisibility(visible: Boolean) {
        pbLoadTasksList.visibility = if (visible) View.VISIBLE else View.GONE
    }
}
