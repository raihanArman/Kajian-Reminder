package id.co.kajianpro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import id.co.dhanapps.view.login.HomeViewModelProviderFactory
import id.co.kajianpro.data.repository.HomeRepository
import id.co.kajianpro.databinding.ActivityMainBinding
import id.co.kajianpro.home.AkunFragment
import id.co.kajianpro.home.HomeFragment
import id.co.kajianpro.home.ReminderFragment
import id.co.kajianpro.service.ReminderCekService
import id.co.kajianpro.viewmodel.HomeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val homeRepository = HomeRepository()
        val viewModelFactory = HomeViewModelProviderFactory(application, homeRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.bottomNav.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.nav_home) {
                setFragment(HomeFragment())
            }else if (item.itemId == R.id.nav_reminder) {
                setFragment(ReminderFragment())
            }else if (item.itemId == R.id.nav_pangkalan) {
                setFragment(AkunFragment())
            }
            true
        })

        setFragment(HomeFragment())

        FirebaseMessaging.getInstance().subscribeToTopic("all");

    }


    fun setFragment(fragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(dataBinding.frameMain.getId(), fragment!!)
        transaction.commit()
    }


    override fun onStart() {
        super.onStart()
        startService(Intent(this@MainActivity, ReminderCekService::class.java))
    }
}