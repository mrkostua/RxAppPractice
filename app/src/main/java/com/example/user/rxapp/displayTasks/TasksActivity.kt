package com.example.user.rxapp.displayTasks

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.user.rxapp.R
import com.example.user.rxapp.data.local.dbRoom.SimpleTaskDO
import com.example.user.rxapp.data.local.dbRoom.TaskDO
import com.example.user.rxapp.displayMain.MainActivity
import com.example.user.rxapp.tools.ConstantValues
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_tasks.*
import javax.inject.Inject

class TasksActivity : DaggerAppCompatActivity(), TasksActivityContract.View {
    private val TAG = this.javaClass.simpleName
    @Inject
    lateinit var presenter: TasksActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        presenter.takeView(this)

        pbWaitForTask.visibility = View.VISIBLE
        presenter.displayNewestTask(ConstantValues.DEFAULT_DELAY_TIME_IN_SECONDS)
    }
    //TODO delete or move to addTaskActivity
    private fun addSomeTasks() {
        presenter.addTask(SimpleTaskDO("Kiss some girl", "first choose the victim\n second perform unexpected attack", false))
        presenter.addTask(SimpleTaskDO("build house on the tree", "First buy the tree \n Second by house \n Third put house on the tree", false))
        presenter.addTask(SimpleTaskDO("build house on the tree", "First buy the tree \n Second by house \n Third put house on the tree", false))
        presenter.addTask(SimpleTaskDO("build house on the tree", "First buy the tree \n Second by house \n Third put house on the tree", false))
        presenter.addTask(SimpleTaskDO("Kiss some girl", "first choose the victim, second perform unexpected attack", false))
        presenter.addTask(SimpleTaskDO("build house on the tree", "First buy the tree \n Second by house \n Third put house on the tree", false))

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeAll()

    }

    override fun displayTask(task: TaskDO) {
        //when task will be displayed start showing progress bar for x seconds
        //each time it will be reset
        pbWaitForTask.visibility = View.GONE
        tvTaskName.text = task.taskName
        tvTaskDescription.text = task.taskDescription

        pbWaitForTask.visibility = View.VISIBLE

    }

    override fun showMainButton() {
        startActivity(Intent(this, MainActivity::class.java))

    }
}
