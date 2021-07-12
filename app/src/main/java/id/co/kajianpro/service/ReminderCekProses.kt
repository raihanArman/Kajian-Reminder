package id.co.kajianpro.service

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import id.co.kajianpro.data.db.AppDatabase
import id.co.kajianpro.data.model.Kajian
import id.co.kajianpro.utils.Constant
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ReminderCekProses(context: Context) : View(context) {

    private val TAG = "ReminderCekProses"

    var idUser: String ?= ""
    var sharedPreferences: SharedPreferences? = null
    var appDatabase: AppDatabase

    init {
        sharedPreferences =
            context.getSharedPreferences(Constant.LOGIN_KEY, Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString(Constant.ID_USER_KEY,"")
        appDatabase = AppDatabase(context)
    }


    private fun prosesHapusReminder() {
        val calendarNow = Calendar.getInstance()
        val kajianList: List<Kajian> =
            appDatabase.databaseDao().getAllKajianReminder(idUser.toString())
        if (kajianList.size > 0) {
            for (kajian in kajianList) {
                val tanggalKajian = Calendar.getInstance()
                tanggalKajian.time = kajian.tanggal
                if (calendarNow.after(tanggalKajian)) {
                    GlobalScope.launch {
                        kajian.idkajian?.let { idUser?.let { it1 ->
                            appDatabase.databaseDao().deleteKajianReminder(it,
                                it1
                            )
                        } }
                    }
                }
            }
        } else {
            Log.d(TAG, "init: Tidak ada reminder")
        }
    }

    val mHandler = object:  Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            prosesHapusReminder()
            sendEmptyMessageDelayed(1, 1000)
        }
    }


}