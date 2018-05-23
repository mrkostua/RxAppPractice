package com.example.user.rxapp.tools

import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 5/23/2018.
 */
fun getNextCalendarDay(calendar: Calendar) =
        if (calendar[Calendar.DAY_OF_WEEK] == Calendar.SATURDAY) {
            Calendar.SUNDAY
        } else {
            calendar[Calendar.DAY_OF_WEEK] + 1
        }