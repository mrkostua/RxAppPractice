package com.example.user.rxapp.mainScreen

import android.os.Bundle
import android.view.View
import com.example.user.rxapp.R
import com.example.user.rxapp.data.local.dbRoom.SimpleTaskDO
import com.example.user.rxapp.data.local.dbRoom.TaskDO
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.observers.DisposableCompletableObserver
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainActivityContract.View {
    private val TAG = this.javaClass.simpleName
    @Inject
    public lateinit var presenter: MainActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeAll()

    }

    fun bLetsStartClickListener(view: View) {
        presenter.addTask(SimpleTaskDO("Kiss some girl", "first choose the victim, second perform unexpected attack", false))
        presenter.addTask(SimpleTaskDO("build house on the tree", "First buy the tree \n Second by house \n Third put house on the tree", false))
        presenter.addTask(SimpleTaskDO("build house on the tree", "First buy the tree \n Second by house \n Third put house on the tree", false))
        presenter.addTask(SimpleTaskDO("build house on the tree", "First buy the tree \n Second by house \n Third put house on the tree", false))
        presenter.addTask(SimpleTaskDO("Kiss some girl", "first choose the victim, second perform unexpected attack", false))
        presenter.addTask(SimpleTaskDO("build house on the tree", "First buy the tree \n Second by house \n Third put house on the tree", false))

        view.visibility = View.GONE
        pbWaitForTask.visibility = View.VISIBLE
        bLetsStart.visibility = View.GONE
        presenter.displayNewestTask()

    }

    override fun displayTask(task: TaskDO) {
        pbWaitForTask.visibility = View.GONE
        tvTaskName.text = task.taskName
        tvTaskDescription.text = task.taskDescription

        pbWaitForTask.visibility = View.VISIBLE

    }

    override fun showMainButton() {
        pbWaitForTask.visibility = View.GONE
        bLetsStart.visibility = View.VISIBLE
        tvTaskName.text = ""
        tvTaskDescription.text = ""


    }
}
