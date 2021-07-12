package id.co.kajianpro.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import id.co.kajianpro.BuildConfig
import id.co.kajianpro.R
import id.co.kajianpro.data.db.AppDatabase
import id.co.kajianpro.data.model.Kajian
import id.co.kajianpro.data.model.Waktu
import id.co.kajianpro.data.repository.RemiderRepository
import id.co.kajianpro.databinding.ActivityKajianDetailBinding
import id.co.kajianpro.receiver.TimeReceiver
import id.co.kajianpro.utils.SetWaktu
import id.co.kajianpro.viewmodel.ReminderViewModel
import id.co.kajianpro.viewmodel.ReminderViewModelFactory
import java.util.*


class KajianDetailActivity : AppCompatActivity() {


    private val TAG = "KajianDetailActivity"
    lateinit var dataBinding : ActivityKajianDetailBinding
    lateinit var viewModel: ReminderViewModel
    var kajian: Kajian ?= null
    var setWaktu: SetWaktu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_kajian_detail)
        val reminderRepository = RemiderRepository(AppDatabase(this))
        val viewModelFactory = ReminderViewModelFactory(application, reminderRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ReminderViewModel::class.java)

        setWaktu = SetWaktu(this)
        kajian = intent.getSerializableExtra("kajian") as Kajian

        val tanggal = DateFormat.format("EEEE, dd MMM yyyy", kajian?.tanggal).toString()
        val jam = DateFormat.format("HH:mm", kajian?.tanggal).toString()

        dataBinding.jam = jam
        dataBinding.tanggal = tanggal

        dataBinding.kajian = kajian


        dataBinding.rvYoutube.setOnClickListener {
            watchYoutubeVideo(this, kajian?.link)
        }

        Glide.with(this)
            .load(BuildConfig.BASE_URL_GAMBAR+kajian?.gambar)
            .into(dataBinding.ivKajian)

        checkReminder()

        viewModel.insertKajianMutable.observe(this, Observer { pesan ->
            Toast.makeText(this, "$pesan", Toast.LENGTH_LONG).show()
            setWaktu()
        })


        viewModel.insertWaktuMutable.observe(this, Observer { pesan ->
            Log.d(TAG, "onCreate: $pesan")
        })

        dataBinding.btnSetReminder.setOnClickListener {
            if (kajian != null) {
                kajian?.idUser = "1"
                viewModel.insertKajianReminder(kajian!!)
            }
        }

    }

    private fun setWaktu() {
        val tanggalKajian: String =
            DateFormat.format("yyyy-MM-dd HH:mm", kajian?.tanggal)
                .toString()

        val calendarMin30 = Calendar.getInstance()
        val calendarMin15 = Calendar.getInstance()
        val calendarMin10 = Calendar.getInstance()
        val calendarScreen = Calendar.getInstance()
        calendarMin30.time = kajian?.tanggal
        calendarMin15.time = kajian?.tanggal
        calendarScreen.time = kajian?.tanggal

        calendarMin15.add(Calendar.MINUTE, -15)
        calendarMin30.add(Calendar.MINUTE, -30)
        calendarMin10.add(Calendar.MINUTE, -10)
        calendarScreen.add(Calendar.SECOND, 3)

        val tanggalKajianMin15 =
            DateFormat.format("yyyy-MM-dd HH:mm", calendarMin15).toString()
        val tanggalKajianMin30 =
            DateFormat.format("yyyy-MM-dd HH:mm", calendarMin30).toString()
        val tanggalKajianScreen =
            DateFormat.format("yyyy-MM-dd HH:mm", calendarScreen).toString()


        val random = Random()
        val randomNumber = random.nextInt(900) + 100
        val randomNumber15 = random.nextInt(900) + 100
        val randomNumber30 = random.nextInt(900) + 100
        val randomNumberScreen = random.nextInt(900) + 100

        setWaktu?.setAlarm(
            tanggalKajian,
            kajian?.namaKajian + "\nSudah dimulai",
            randomNumber,
            kajian,
            TimeReceiver.TYPE_ALARM_SCREEN
        )
        setWaktu?.setAlarm(
            tanggalKajianMin15,
            kajian?.namaKajian + "\nTinggal 15 menit lagi",
            randomNumber15,
            kajian,
            TimeReceiver.TYPE_ALARM_NOTIF
        )
        setWaktu?.setAlarm(
            tanggalKajianMin30,
            kajian?.namaKajian + "\nTinggal 30 menit lagi",
            randomNumber30,
            kajian,
            TimeReceiver.TYPE_ALARM_NOTIF
        )
        val waktu1 = Waktu(kajian?.idkajian, randomNumber.toString())
        viewModel.insertWaktu(waktu1)

        val waktu2 = Waktu(kajian?.idkajian, randomNumber15.toString())
        viewModel.insertWaktu(waktu2)

        val waktu3 = Waktu(kajian?.idkajian, randomNumber30.toString())
        viewModel.insertWaktu(waktu3)

        Toast.makeText(this, "Berhasil di set", Toast.LENGTH_LONG).show()
        dataBinding.btnSetReminder.visibility = View.INVISIBLE

    }

    private fun checkReminder() {
        val calendar = Calendar.getInstance()
        if(calendar.time.before(kajian?.tanggal)) {
            if (viewModel.checkKajianReminder(kajian?.idkajian!!, "1")) {
                dataBinding.btnSetReminder.visibility = View.INVISIBLE
            } else {
                dataBinding.btnSetReminder.visibility = View.VISIBLE
            }
        }else{
            dataBinding.btnSetReminder.visibility = View.INVISIBLE
        }
    }

    fun watchYoutubeVideo(context: Context, id: String?) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$id")
        )
        try {
            context.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }
    }
}