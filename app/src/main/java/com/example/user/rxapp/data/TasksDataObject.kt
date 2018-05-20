package com.example.user.rxapp.data

/**
 * @author Kostiantyn Prysiazhnyi on 5/20/2018.
 */

class TasksDataObject(var eatTimes: Int, var teethTimes: Int, var learningTimeHours: Int,
                      var learningTimeMinutes: Int, var negativeInfoFromDay: String = "") {
    override fun toString(): String {
        return "TasksDataObject(eatTimes=$eatTimes, teethTimes=$teethTimes, learningTimeHours=$learningTimeHours, " +
                "learningTimeMinutes=$learningTimeMinutes, negativeInfoFromDay='$negativeInfoFromDay')"
    }
}