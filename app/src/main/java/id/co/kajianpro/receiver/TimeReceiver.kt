package id.co.kajianpro.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.PowerManager
import android.text.format.DateFormat
import android.util.Log
import androidx.core.app.NotificationCompat
import id.co.kajianpro.MainActivity
import id.co.kajianpro.R
import id.co.kajianpro.data.model.Kajian
import id.co.kajianpro.home.KajianReminderScreenActivity
import id.co.kajianpro.utils.Constant
import id.co.kajianpro.utils.SetWaktu
import java.util.*

class TimeReceiver: BroadcastReceiver() {

    private val TAG = "TimeReceiver"

    var idTime = 0
    var typeAlarm:Int = 0
    var kajian: Kajian? = null

    companion object{

        val TYPE_ALARM_SCREEN = 965
        val TYPE_ALARM_NOTIF = 975
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val powerManager =
            context!!.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock =
            powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag")
        wakeLock.acquire()
        val action = intent!!.action

        if(action == "databaru") {
            val pesan = intent.getStringExtra("pesan")
            sendNotification(
                context!!,
                "Kajian Terbaru",
                pesan!!
            )
        }else{
            val tanggal = intent!!.getStringExtra("tanggal")
            val judulKajian = intent!!.getStringExtra("judul_kajian")
            kajian = intent!!.getSerializableExtra("kajian") as Kajian
            typeAlarm = intent!!.getIntExtra(Constant.ALARM_TYPE_INTENT, 0)
            idTime = intent!!.getIntExtra("id_time", 0)
            sendNotification(
                context!!,
                context!!.resources.getString(R.string.app_name),
                intent!!.getStringExtra("judul_kajian")!!
            )
            SetWaktu(context!!).setAlarm(tanggal, judulKajian, idTime, kajian, typeAlarm)
            Log.d(TAG, "onReceive: Tangkap")
        }
    }

    private fun sendNotification(
        context: Context,
        title: String,
        desc: String
    ) {
        Log.d(TAG, "sendNotification: mucul notif")
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val newIntent = Intent(context, MainActivity::class.java)
        newIntent.putExtra("notifkey", "notifvalue")
        val pendingIntent =
            PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val NOTIFICATION_CHANNEL_ID = "rasupe_channel_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val important = NotificationManager.IMPORTANCE_HIGH
            val NOTIFICATION_CHANNEL_NAME = "rasupe channel"
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, important
            )
            notificationManager.createNotificationChannel(channel)
        }
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder =
            NotificationCompat.Builder(context, desc)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(uri)
                .setAutoCancel(true)

        val NOTIF_ID = 998
        notificationManager.notify(NOTIF_ID, builder.build())

        SetWaktu(context).stopAlarmManager(idTime)

        if (typeAlarm == TYPE_ALARM_SCREEN) {
            val intent =
                Intent(context.applicationContext, KajianReminderScreenActivity::class.java)
            intent.putExtra("kajian", kajian)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

}