package com.example.user.rxapp.tools

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast

/**
 * @author Kostiantyn Prysiazhnyi on 5/24/2018.
 */
object DisplayingHelper {

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
}