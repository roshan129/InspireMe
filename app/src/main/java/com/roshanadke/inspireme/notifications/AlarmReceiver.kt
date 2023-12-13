package com.roshanadke.inspireme.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.roshanadke.inspireme.R
import com.roshanadke.inspireme.common.Constants
import com.roshanadke.inspireme.common.Constants.CHANNEL_ID
import com.roshanadke.inspireme.common.Constants.NOTIFICATION_ID
import com.roshanadke.inspireme.common.hasNotificationPermission
import com.roshanadke.inspireme.presentation.viewmodel.QuotesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {

    val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context?, p1: Intent?) {
        Log.d("TAG", "onReceive: received:  reciever")
        showNotification(context)
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(context: Context?) {
        scope.launch {
            val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
                .setSmallIcon(R.drawable.letter_i)
                .setContentText("It's time for your daily notification!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            with(NotificationManagerCompat.from(context)) {
                if(context.hasNotificationPermission()) {
                    notify(NOTIFICATION_ID, builder.build())
                }
            }
        }

    }
}
