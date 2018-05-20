package com.example.user.rxapp.data

/**
 * @author Kostiantyn Prysiazhnyi on 5/20/2018.
 */
class TasksDataHelper {
    companion object {

        fun getCurrentDayTaskData(): TasksDataObject {
            return TasksDataObject(3, 1, 2, 22)
        }
    }
}