package id.co.kajianpro.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ReminderCekService: Service() {
    private val TAG = "ReminderCekService"

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        ReminderCekProses(applicationContext).mHandler.sendEmptyMessage(0)
        Log.d(TAG, "onStartCommand: Service aktif")
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Service hancur")
    }
}