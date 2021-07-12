package id.co.kajianpro.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import id.co.kajianpro.data.model.Kajian
import id.co.kajianpro.home.KajianReminderScreenActivity
import id.co.kajianpro.receiver.TimeReceiver
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SetWaktu(val context: Context) {

    private val TAG = "SetWaktu"


    fun setAlarmScreen(tanggal: String?, kajian: Kajian?, idTime: Int) {
        val time: Long
        val intent =
            Intent(context.getApplicationContext(), KajianReminderScreenActivity::class.java)
        intent.putExtra("kajian", kajian)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            context.getApplicationContext(),
            idTime, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val sdf =
            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        try {
            calendar.time = sdf.parse(tanggal)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val calendarNow = Calendar.getInstance()
        time = if (calendarNow.before(calendar)) {
            calendar.timeInMillis
        } else {
            calendar.add(Calendar.DATE, 1)
            calendar.timeInMillis
        }
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC, time, pendingIntent)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alarmManager.setExact(AlarmManager.RTC, time, pendingIntent)
        } else {
            alarmManager[AlarmManager.RTC, time] = pendingIntent
        }
    }

    fun setAlarm(
        tanggal: String?,
        judulKajian: String?,
        idTime: Int,
        kajian: Kajian?,
        typeAlarm: Int
    ) {
        val time: Long
        val intent = Intent(context, TimeReceiver::class.java)
        intent.putExtra("tanggal", tanggal)
        intent.putExtra("kajian", kajian)
        intent.putExtra("judul_kajian", judulKajian)
        intent.putExtra(Constant.ALARM_TYPE_INTENT, typeAlarm)
        intent.putExtra("id_time", idTime)
        val pendingIntent = PendingIntent.getBroadcast(context, idTime, intent, 0)
        val sdf =
            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        try {
            calendar.time = sdf.parse(tanggal)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val calendarNow = Calendar.getInstance()
        time = if (calendarNow.before(calendar)) {
            calendar.timeInMillis
        } else {
            calendar.add(Calendar.DATE, 1)
            calendar.timeInMillis
        }
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent)
        } else {
            alarmManager[AlarmManager.RTC_WAKEUP, time] = pendingIntent
        }
        Log.i(TAG, "set alarm manager")
    }

    fun stopAlarmManager(idTime: Int) {
        val alermIntent = Intent(context, TimeReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, idTime, alermIntent, 0)
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        Log.i(TAG, "alarm manager dimatikan")
    }

    fun stopAlarmScreenManager(idTime: Int) {
        val alermIntent = Intent(context, KajianReminderScreenActivity::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            idTime,
            alermIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        Log.i(TAG, "alarm manager screen dimatikan")
    }


}