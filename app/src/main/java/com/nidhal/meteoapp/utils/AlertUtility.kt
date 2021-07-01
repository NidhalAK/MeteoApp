package com.nidhal.meteoapp.utils

import android.app.Activity
import android.app.AlertDialog


object AlertUtility {
    /**
     * * Show custom alert with title and Neutral button
     * @param activity
     * @param title
     * @param message
     * @param textButton
     */
    fun showNeutralAlert(
        activity: Activity?,
        title: String?,
        message: String,
        textButton: String,
        buttonCallback: NoticeDialogListener
    ) {
        if (activity != null && !activity.isFinishing) {
            AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(
                    textButton
                ) { dialogInterface, _ ->
                    buttonCallback.onButtonClick(
                        dialogInterface
                    )
                }.show()
        }
    }
}