package com.example.user.rxapp.displayTasks

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import com.example.user.rxapp.R
import com.example.user.rxapp.data.local.dbRoom.TaskDo
import com.example.user.rxapp.displayMain.MainActivity
import com.example.user.rxapp.tools.ConstantValues
import com.example.user.rxapp.tools.DisplayingHelper
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_tasks.*
import java.text.DateFormat
import javax.inject.Inject

class TasksActivity : DaggerAppCompatActivity(), TasksActivityContract.View {
    private val TAG = this.javaClass.simpleName
    @Inject
    lateinit var presenter: TasksActivityContract.Presenter
    private val dateFormat = DateFormat.getDateInstance()
    private val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
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
        drawableForPB.setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN)
        pbTimeToRead.progressDrawable = drawableForPB

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeAll()

    }

    override fun displayTask(task: TaskDo) {
        pbTimeToRead.progress = 0
        pbTimeToRead.visibility = View.VISIBLE

        tvTaskName.text = task.taskName
        tvTaskDescription.text = task.taskDescription

        tvDeadline.visibility = View.VISIBLE
        tvDate.text = dateFormat.format(task.deadLineDate)
        tvTime.text = timeFormat.format(task.deadLineDate)


    }

    override fun showMainButton() {
        pbTimeToRead.visibility = View.GONE
        startActivity(Intent(this, MainActivity::class.java))

    }

    override fun sendMessageWithDelay(delayInSec: Int) {
        handler.sendMessageDelayed(Message.obtain(handler, handlerUpdateProgressBar, 0, 0), delayInSec.toLong() * 1000)
    }

    override fun showToast(sms: String) {
        DisplayingHelper.showToast(this, sms)
    }


}
