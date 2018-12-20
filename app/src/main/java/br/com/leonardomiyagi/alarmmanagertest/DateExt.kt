package br.com.leonardomiyagi.alarmmanagertest

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lmiyagi on 20/12/18.
 */
fun Date.formatToDefault(context: Context): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this)
}

fun Date.getTime(context: Context): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)
}