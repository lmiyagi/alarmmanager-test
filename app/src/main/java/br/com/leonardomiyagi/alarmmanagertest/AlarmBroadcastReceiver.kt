package br.com.leonardomiyagi.alarmmanagertest

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat

/**
 * Created by lmiyagi on 19/12/18.
 */
class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null && intent != null) {

            val mBuilder = NotificationCompat.Builder(context, "Some")
                .setContentTitle("My notification")
                .setContentText("This should be working...")

            val showTextIntent = Intent(context, MainActivity::class.java)
            showTextIntent.putExtra(EXTRA_MESSAGE, intent.getStringExtra(EXTRA_MESSAGE))

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