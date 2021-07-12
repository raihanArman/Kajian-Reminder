package id.co.kajianpro.home

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.text.format.DateFormat
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import id.co.kajianpro.BuildConfig
import id.co.kajianpro.R
import id.co.kajianpro.data.model.Kajian
import id.co.kajianpro.databinding.ActivityKajianReminderScreenBinding

class KajianReminderScreenActivity : AppCompatActivity() {

    var kajian: Kajian? = null
    private lateinit var dataBinding: ActivityKajianReminderScreenBinding
    var vibrator: Vibrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_kajian_reminder_screen)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator!!.vibrate(10000)

        kajian = intent.getSerializableExtra("kajian") as Kajian

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN or
                            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        }
        dataBinding.tvJudulKajian.setText(kajian?.namaKajian)
        val jam: String = DateFormat.format("HH:mm", kajian?.tanggal).toString()
        dataBinding.tvJam.setText(jam)
        Glide.with(this).load(BuildConfig.BASE_URL_GAMBAR.toString() + "pamflet/" + kajian?.gambar).into(dataBinding.ivKajian)
        Glide.with(this).load(BuildConfig.BASE_URL_GAMBAR.toString() + "pamflet/" + kajian?.gambar).into(dataBinding.ivBackground)


        dataBinding.btnMatikan.setOnClickListener(View.OnClickListener { finish() })
    }


    override fun onPause() {
        super.onPause()
        vibrator!!.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        vibrator!!.cancel()
    }

}