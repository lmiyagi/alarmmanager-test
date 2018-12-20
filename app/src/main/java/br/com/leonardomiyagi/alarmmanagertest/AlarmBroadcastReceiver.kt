package br.com.leonardomiyagi.alarmmanagertest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat

/**
 * Created by lmiyagi on 19/12/18.
 */
class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null && intent != null) {

            val mBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationCompat.Builder(context, NotificationChannel.DEFAULT_CHANNEL_ID)
            } else {
                NotificationCompat.Builder(context)
            }
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("This should be working...")

            val showTextIntent = Intent(context, MainActivity::class.java)
            showTextIntent.putExtra(EXTRA_MESSAGE, intent.getStringExtra(EXTRA_MESSAGE))
            showTextIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)

            val resultPendingIntent = PendingIntent.getActivity(
                context,
                REQUEST_CODE_MESSAGE,
                showTextIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            mBuilder.setContentIntent(resultPendingIntent)

            val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            mNotificationManager.notify(NOTIFICATION_MESSAGE, mBuilder.build())
        }
    }
}