package com.example.user.rxapp.firstScreen

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.example.user.rxapp.R
import com.example.user.rxapp.addTask.AddTaskActivity
import com.example.user.rxapp.data.local.dbRoom.SimpleTaskDO
import com.example.user.rxapp.displayTasks.TasksActivity
import com.example.user.rxapp.tools.DisplayingHelper
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainActivityContract.View {
    @Inject
    public lateinit var presenter: MainActivityContract.Presenter

    private val removeActionsTypes = arrayOf("Delete today tasks", "Delete all tasks")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        presenter.takeView(this)
        presenter.start()
    }

    private fun initializeViews() {
        pbMainActivity.indeterminateDrawable.setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.MULTIPLY)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeAll()
    }

    override fun showToast(msg: String) {
        DisplayingHelper.showToast(this, msg)
    }

    fun bShowTasksClickListener(view: View) {
        presenter.startTaskActivity()
    }

    override fun startTaskActivity() {
        startActivity(Intent(this, TasksActivity::class.java))
    }

    fun bDeleteTasksClickListener(view: View) {
        var clickedPosition = 0
        DisplayingHelper.showSingleChoiceAlertDialog(this, "Choose delete action",
                removeActionsTypes, DialogInterface.OnClickListener { d, position ->
            clickedPosition = position

        }, DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    if (!removeActionsTypes.isEmpty()) {
                        chooseDeleteAction(clickedPosition)
                        dialog.dismiss()
                    }
                }
                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()

            }
        })
    }

    fun bAddTaskClickListener(view: View) {
        //starts activity to add simple task
        startActivity(Intent(this, AddTaskActivity::class.java))
    }

    private fun chooseDeleteAction(clickedPosition: Int) {
        when (removeActionsTypes[clickedPosition]) {
            removeActionsTypes[0] -> {
                presenter.deleteTodayTasks()
            }
            removeActionsTypes[1] -> {
                presenter.deleteAllTasks()
            }
        }
    }

    override fun showFailedDialog(message: String) {
        DisplayingHelper.showCustomAlertDialog(this, "Deletion failed", message,
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
    }

    override fun setPBVisibility(visible: Boolean) {
        pbMainActivity.visibility = if (visible) View.VISIBLE else View.GONE
    }
}
