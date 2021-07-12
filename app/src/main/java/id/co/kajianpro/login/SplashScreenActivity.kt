package id.co.kajianpro.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import id.co.kajianpro.MainActivity
import id.co.kajianpro.R
import id.co.kajianpro.databinding.ActivitySplashScreenBinding
import id.co.kajianpro.utils.Constant

class SplashScreenActivity : AppCompatActivity() {
    lateinit var dataBinding: ActivitySplashScreenBinding
    val time_loading = 3000L
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        sharedPreferences = getSharedPreferences(Constant.LOGIN_KEY, Context.MODE_PRIVATE)


        Handler(Looper.getMainLooper()).postDelayed({
            cekLogin()
        }, time_loading)

    }

    private fun cekLogin() {
        val statusLogin = sharedPreferences?.getBoolean(Constant.LOGIN_STATUS, false)
        if(statusLogin!!){
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }else{
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}