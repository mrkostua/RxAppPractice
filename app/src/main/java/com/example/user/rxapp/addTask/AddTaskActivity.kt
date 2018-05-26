package com.example.user.rxapp.addTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.user.rxapp.R
import com.example.user.rxapp.data.local.dbRoom.SimpleTaskDO
import com.example.user.rxapp.data.local.dbRoom.TaskDo
import com.example.user.rxapp.tools.DisplayingHelper
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*
import javax.inject.Inject

class AddTaskActivity : DaggerAppCompatActivity(), AddTaskContract.View {
    private val TAG = this.javaClass.simpleName
    private val calendar = Calendar.getInstance()
    private lateinit var taskToSave: TaskDo
    @Inject
    public lateinit var presenter: AddTaskContract.Presenter

    init {
        calendar.timeInMillis = System.currentTimeMillis()
        taskToSave = TaskDo(null, "", "", false, Date(calendar.timeInMillis))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        presenter.takeView(this)
        presenter.start()
        initializeViews()
    }

    private fun initializeViews() {
        tvTextDate.text = calendar[Calendar.YEAR].toString() + " . " + calendar[Calendar.MONTH].toString() +
                " . " + calendar[Calendar.DAY_OF_MONTH].toString()
        tvTextTime.text = calendar[Calendar.HOUR_OF_DAY].toString() + " : " + calendar[Calendar.MINUTE].toString()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeAll()
    }

    fun bSetDateClickListener(v: View) {
        DisplayingHelper.showDatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            tvTextDate.text = dayOfMonth.toString() + " . " + month.toString() + " . " + year.toString()
            calendar[Calendar.YEAR] = year
            calendar[Calendar.MONTH] = month
            calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        })
    }

    fun bSetTimeClickListener(v: View) {
        DisplayingHelper.showTimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            tvTextTime.text = hourOfDay.toString() + " : " + minute.toString()
            calendar[Calendar.HOUR_OF_DAY] = hourOfDay
            calendar[Calendar.MINUTE] = minute
        })
    }

    fun bSaveTaskClickListener(v: View) {
        taskToSave.deadLineDate = Date(calendar.timeInMillis)

        taskToSave.taskName = etTaskName.text.toString()
        taskToSave.taskDescription = etTaskDescription.text.toString()
        if (taskToSave.taskName.isEmpty()) {
            showToast("first set task name")
            return
        }
        if (taskToSave.taskDescription.isEmpty()) {
            showToast("first set task description")
            return
        }
        presenter.addTask(taskToSave)

    }

    override fun showToast(sms: String) {
        DisplayingHelper.showToast(this, sms)
    }

    override fun showFailedSavingTaskDialog(message: String, failedTask: TaskDo) {
        DisplayingHelper.showCustomAlertDialog(this, "Failed to save Task", message,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            presenter.addTask(failedTask)
                            dialog.dismiss()
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                        }
                    }
                })
    }

}
