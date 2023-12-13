package com.roshanadke.inspireme.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.roshanadke.inspireme.common.Constants.PENDING_INTENT_REQUEST_CODE


fun setDailyNotification(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val alarmIntent = Intent(context, AlarmReceiver::class.java)

    alarmIntent.action = "com.roshanadke.inspireme.notify_alarm"

    val pendingIntent = PendingIntent.getBroadcast(context, PENDING_INTENT_REQUEST_CODE,
        alarmIntent, PendingIntent.FLAG_IMMUTABLE)

    val calendar = java.util.Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 17)
    calendar.set(java.util.Calendar.MINUTE, 45)

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
}
