package com.example.user.rxapp.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 5/20/2018.
 */
@Entity(tableName = "tasks")
data class TaskDataObject(@PrimaryKey(autoGenerate = true) var id: Int? = null,
                          var eatTimes: Int,
                          var teethTimes: Int,
                          var learningTimeHours: Int,
                          var learningTimeMinutes: Int,
                          var isDone: Boolean,
                          val date: Date) {
    override fun toString(): String {
        return "TasksDataObject(eatTimes=$eatTimes, teethTimes=$teethTimes, learningTimeHours=$learningTimeHours, " +
                "learningTimeMinutes=$learningTimeMinutes')"
    }
}