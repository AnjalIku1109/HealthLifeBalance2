package com.example.healthassist.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.healthassist.R

class ReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (DataModel.displayReminderWhenLocked()) {
            if (Build.VERSION.SDK_INT >= 27) {
                setShowWhenLocked(true)
            } else @Suppress("DEPRECATION") {
                window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
            }
        }

        // This activity's title briefly flashes on the screen after we dismiss the dialog.
        // So we set it to the empty string as a workaround.
        setTitle("")

        // Don't call setContentView. We apparently don't need it
        // setContentView(/*...*/)

        @SuppressLint("InflateParams")
        val dialogContents = layoutInflater.inflate(R.layout.reminder_dialog, null)

        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.reminder_title))
            .setView(dialogContents)
            .setNegativeButton(getString(R.string.reminder_snooze), null)
            .setOnCancelListener { finish() }
            .setOnDismissListener { finish() }
            .show()

        val okButton: Button = dialogContents.findViewById(R.id.button)
        okButton.setOnClickListener { clickOk(); dialog.dismiss() }
    }

    private fun clickOk() {
        if (!DataModel.hasTakenDrugToday()) {
            DataModel.takeDrugNow()
        }
        Notifications.possiblyCancelTheNotification()
    }
}