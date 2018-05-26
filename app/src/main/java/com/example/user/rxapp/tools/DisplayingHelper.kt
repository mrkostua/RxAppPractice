package com.example.user.rxapp.tools

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.TimePicker
import android.widget.Toast
import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 5/24/2018.
 */
object DisplayingHelper {
    private val calendar = Calendar.getInstance()
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

    }

    fun showCustomAlertDialog(context: Context, title: String, message: String,
                              buttonsClickListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Do", buttonsClickListener)
                .setNegativeButton("Back", buttonsClickListener).create().show()
    }

    fun showSingleChoiceAlertDialog(context: Context, title: String,
                                    items: Array<String>,
                                    itemsCL: DialogInterface.OnClickListener,
                                    buttonsCL: DialogInterface.OnClickListener) {
        AlertDialog.Builder(context)
                .setTitle(title)
                .setSingleChoiceItems(items, 0, itemsCL)
                .setPositiveButton("Do", buttonsCL)
                .setNegativeButton("Back", buttonsCL).create().show()

    }

    fun showDatePickerDialog(context: Context, listener: DatePickerDialog.OnDateSetListener) {
        calendar.timeInMillis = System.currentTimeMillis()
        DatePickerDialog(context, listener, calendar[Calendar.YEAR],
                calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
    }

    fun showTimePickerDialog(context: Context, listener: TimePickerDialog.OnTimeSetListener) {
        calendar.timeInMillis = System.currentTimeMillis()
        TimePickerDialog(context, listener, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE],
                true).show()
    }
}