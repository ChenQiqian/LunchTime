package com.thss.lunchtime.component

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.thss.lunchtime.R
import android.content.Context
import android.content.Intent
import com.thss.lunchtime.MainActivity

const val CHANNEL_ID = "lunchTime"
fun showNotification(context: Context, title: String, content: String) {

    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.forumicon)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

        }
        notify(1, builder.build())
    }
}

// 为了兼容Android 8.0及更高版本，传递通知之前，必须在系统中注册应用程序的通知通道。创建好后在 onCreate 函数内调用
private fun createNotificationChannel(context: Context) {
    val channel = NotificationChannel(CHANNEL_ID, "lunchTime", NotificationManager.IMPORTANCE_HIGH).apply { description = "LUNCHTIME" }
    // Register the channel with the system
    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}