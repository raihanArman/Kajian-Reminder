package id.co.kajianpro.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.co.kajianpro.data.db.AppDatabase
import id.co.kajianpro.data.model.Kajian
import id.co.kajianpro.data.model.Waktu
import id.co.kajianpro.data.repository.RemiderRepository
import kotlinx.coroutines.launch

class ReminderViewModel (val app: Application, val reminderRepository: RemiderRepository): AndroidViewModel(app){

    val insertKajianMutable: MutableLiveData<String> = MutableLiveData()
    val deleteKajianMutable: MutableLiveData<String> = MutableLiveData()

    val insertWaktuMutable: MutableLiveData<String> = MutableLiveData()
    val deleteWaktuMutable: MutableLiveData<String> = MutableLiveData()

    fun getAllWaktu(idKajian: String) = reminderRepository.getAllWaktu(idKajian)
    fun getAllKajianReminder(idUser: String) = reminderRepository.getAllKajianReminder(idUser)

    fun checkKajianReminder(idKajian: String, idUser: String) = reminderRepository.checkKajianReminder(idKajian, idUser)

    fun insertWaktu(waktu: Waktu) = viewModelScope.launch {
        val insert = reminderRepository.insertWaktu(waktu)
        if (insert > 0){
            Log.d("ahjsoiahjisoa", "insertWaktu: Sukses Insert")
            insertWaktuMutable.postValue("Sukses Insert")
        }else{
            Log.d("ahjsoiahjisoa", "insertWaktu: Gagal Insert")
            insertWaktuMutable.postValue("Gagal Insert")
        }
    }


    fun insertKajianReminder(kajian: Kajian) = viewModelScope.launch {
        val insert = reminderRepository.insertKajianReminder(kajian)
        if (insert > 0){
            Log.d("ahjsoiahjisoa", "insertLocalTrxHariIni: Sukses Insert")
            insertKajianMutable.postValue("Sukses Insert")
        }else{
            Log.d("ahjsoiahjisoa", "insertLocalTrxHariIni: Gagal Insert")
            insertKajianMutable.postValue("Gagal Insert")
        }
    }

    fun deleteKajianReminder(idKajian: String, idUser: String) = viewModelScope.launch {
        reminderRepository.deleteKajianReminder(idKajian, idUser)
        reminderRepository.deleteWaktu(idKajian)
        deleteKajianMutable.postValue("Sukses delete")
    }

}