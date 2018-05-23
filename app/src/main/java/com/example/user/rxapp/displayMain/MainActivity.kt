package com.example.user.rxapp.displayMain

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.user.rxapp.R
import com.example.user.rxapp.displayTasks.TasksActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun bLetsStartClickListener(view: View) {
        startActivity(Intent(this, TasksActivity::class.java))
    }
}
