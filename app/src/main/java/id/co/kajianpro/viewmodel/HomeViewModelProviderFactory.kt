package id.co.dhanapps.view.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.co.kajianpro.data.repository.HomeRepository
import id.co.kajianpro.data.repository.LoginRepository
import id.co.kajianpro.viewmodel.HomeViewModel

class HomeViewModelProviderFactory(val app: Application, val homeRepository: HomeRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(app, homeRepository) as T
    }
}