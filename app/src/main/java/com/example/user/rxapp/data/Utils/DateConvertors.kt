package com.example.user.rxapp.data.Utils

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 5/21/2018.
 */

object DateConvertors {
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?) =
            if (value == null) {
                null
            } else {
                Date(value)
            }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?) = date?.time
}