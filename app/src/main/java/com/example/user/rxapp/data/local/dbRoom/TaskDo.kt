package com.example.user.rxapp.data.local.dbRoom

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 5/20/2018.
 */
@Entity(tableName = "tasks")
data class TaskDo(@PrimaryKey(autoGenerate = true) var id: Int? = null,
                  var taskName: String,
                  var taskDescription: String,
                  var isDone: Boolean,
                  var deadLineDate: Date)