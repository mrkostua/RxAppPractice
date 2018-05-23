package com.example.user.rxapp.displayTasks

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
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

    private val handlerUpdateProgressBar = 5
    private val handler = Handler {
        if (it.what == handlerUpdateProgressBar) {
            pbTimeToRead.progress = pbTimeToRead.progress + (100 / ConstantValues.DEFAULT_DELAY_TIME_IN_SECONDS).toInt()

        }

        return@Handler it.arg1 == 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        initializeViews()
        presenter.takeView(this)

        presenter.displayNewestTask(ConstantValues.DEFAULT_DELAY_TIME_IN_SECONDS)
    }

    private fun initializeViews() {
        pbTimeToRead.max = 100
        val drawableForPB = pbTimeToRead.progressDrawable.mutate()
        drawableForPB.setColorFilter(Color.BLUE,android.graphics.PorterDuff.Mode.SRC_IN)
        pbTimeToRead.progressDrawable = drawableForPB
        
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
        pbTimeToRead.progress = 0
        pbTimeToRead.visibility = View.VISIBLE

        tvTaskName.text = task.taskName
        tvTaskDescription.text = task.taskDescription

    }

    override fun showMainButton() {
        pbTimeToRead.visibility = View.GONE
        startActivity(Intent(this, MainActivity::class.java))

    }

    override fun sendMessageWithDelay(delayInSec: Int) {
        handler.sendMessageDelayed(Message.obtain(handler, handlerUpdateProgressBar, 0, 0), delayInSec.toLong() * 1000)
    }
}
