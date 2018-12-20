package br.com.leonardomiyagi.alarmmanagertest

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var date: Calendar? = null
    private var dateSelected: Boolean = false
    private var timeSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setClickListeners()
        onIntentReceived(intent)
    }

    private fun setClickListeners() {
        dateEditText.setOnClickListener {
            if (date == null) {
                date = Calendar.getInstance()
                date!!.timeZone = TimeZone.getDefault()
            }
            DialogUtils.showDatePickerDialog(this, { year, month, day ->
                date!!.set(Calendar.YEAR, year)
                date!!.set(Calendar.MONTH, month)
                date!!.set(Calendar.DAY_OF_MONTH, day)
                dateSelected = true
                dateEditText.setText(date!!.time.formatToDefault(this))
            }, date!!)
        }
        timeEditText.setOnClickListener {
            if (date == null) {
                date = Calendar.getInstance()
                date!!.timeZone = TimeZone.getDefault()
            }
            DialogUtils.showTimePickerDialog(this, { hour, minute ->
                date!!.set(Calendar.HOUR_OF_DAY, hour)
                date!!.set(Calendar.MINUTE, minute)
                timeSelected = true
                timeEditText.setText(date!!.time.getTime(this))
            }, null, null, true)
        }
        setAlarmBtn.setOnClickListener {
            if (dateSelected && timeSelected && date != null) {
                val alarmIntent = Intent(this, AlarmBroadcastReceiver::class.java)
                alarmIntent.putExtra(EXTRA_MESSAGE, messageEditText.text.toString())
                val pendingIntent = PendingIntent.getBroadcast(
                    this,
                    REQUEST_CODE_ALARM,
                    alarmIntent,
                    0
                )

                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, date!!.timeInMillis, pendingIntent)
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, date!!.timeInMillis, pendingIntent)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        onIntentReceived(intent)
    }

    private fun onIntentReceived(intent: Intent?) {
        receivedMessageTextView.text = intent?.getStringExtra(EXTRA_MESSAGE)
    }
}
