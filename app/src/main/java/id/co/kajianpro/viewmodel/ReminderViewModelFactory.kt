package id.co.kajianpro.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.co.kajianpro.data.repository.RemiderRepository

class ReminderViewModelFactory(val app: Application, val remindRepository: RemiderRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReminderViewModel(app, remindRepository) as T
    }
}