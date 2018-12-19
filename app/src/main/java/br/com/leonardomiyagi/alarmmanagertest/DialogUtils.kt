package br.com.leonardomiyagi.alarmmanagertest

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.util.*

/**
 * Created by lmiyagi on 19/12/18.
 */
object DialogUtils {

    fun showDatePickerDialog(
        context: Context?,
        onDatePicked: (year: Int, month: Int, day: Int) -> Unit,
        selectedDate: Calendar = Calendar.getInstance(),
        minDate: Date? = null,
        maxDate: Date? = null,
        disableBeforeToday: Boolean = true
    ) {
        val datePickerDialog = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                onDatePicked(year, month, day)
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        minDate?.apply { datePickerDialog.datePicker.minDate = this.time }
        maxDate?.apply { datePickerDialog.datePicker.maxDate = this.time }
        if (disableBeforeToday && minDate == null) {
            datePickerDialog.datePicker.minDate = Date().time
        }
        datePickerDialog.show()
    }

    fun showTimePickerDialog(
        context: Context?,
        onTimePicked: (hour: Int, minute: Int) -> Unit,
        selectedHourOfDay: Int? = null,
        selectedMinute: Int? = null,
        is24HourView: Boolean = false
    ) {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            context,
            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                onTimePicked(hour, minute)
            },
            selectedHourOfDay ?: calendar.get(Calendar.HOUR_OF_DAY),
            selectedMinute ?: calendar.get(Calendar.MINUTE),
            is24HourView
        )
        timePickerDialog.show()
    }
}