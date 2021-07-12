package id.co.kajianpro.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.co.kajianpro.data.model.*
import id.co.kajianpro.data.repository.HomeRepository
import id.co.kajianpro.data.repository.LoginRepository
import id.co.kajianpro.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class HomeViewModel(val app: Application, val homeRepository: HomeRepository): AndroidViewModel(app) {
    val kajianAllMutable: MutableLiveData<Resource<ResponseList<Kajian>>> = MutableLiveData()
    val kajianDetailMutable: MutableLiveData<Resource<ResponseItem<Kajian>>> = MutableLiveData()
    val userProfilByIdMutable: MutableLiveData<Resource<ResponseItem<User>>> = MutableLiveData()

    var userProfilByIdResponse: ResponseItem<User> ?= null
    var kajianAllRespopnse: ResponseList<Kajian>?= null
    var kajianDetailResponse: ResponseItem<Kajian>?= null

    val homeMutable: MutableLiveData<Resource<Home>> = MutableLiveData()


    fun getUserProfil(idUser: String) = viewModelScope.launch {
        safeUserProfil(idUser)
    }

    private suspend fun safeUserProfil(idUser: String) {
        userProfilByIdMutable.postValue(Resource.Loading())

        try{
            if (hasInternetConnection()){
                val response = homeRepository.getProfilUserById(idUser)
                userProfilByIdMutable.postValue(handleUserProfil(response))
            }else{
                userProfilByIdMutable.postValue(Resource.Error("Tidak ada koneksi internet"))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> userProfilByIdMutable.postValue(Resource.Error("Network Failure"))
                else -> userProfilByIdMutable.postValue(Resource.Error("${t.message}"))
            }
        }
    }

    private fun handleUserProfil(response: Response<ResponseItem<User>>): Resource<ResponseItem<User>>? {
        if(response.isSuccessful){
            response.body()?.let {
                userProfilByIdResponse = it

                return Resource.Success(userProfilByIdResponse ?: it)
            }
        }else{
            return Resource.Error(response.message())
        }

        return Resource.Error(response.message())
    }

    fun getKajianDetail(idKajian: String) = viewModelScope.launch {
        safeKajianDetail(idKajian)
    }

    private suspend fun safeKajianDetail(idKajian: String) {
        kajianDetailMutable.postValue(Resource.Loading())

        try{
            if (hasInternetConnection()){
                val response = homeRepository.getKajianDetail(idKajian)
                kajianDetailMutable.postValue(handleKajianDetailResponse(response))
            }else{
                kajianDetailMutable.postValue(Resource.Error("Tidak ada koneksi internet"))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> kajianDetailMutable.postValue(Resource.Error("Network Failure"))
                else -> kajianDetailMutable.postValue(Resource.Error("${t.message}"))
            }
        }
    }

    private fun handleKajianDetailResponse(response: Response<ResponseItem<Kajian>>): Resource<ResponseItem<Kajian>>? {
        if(response.isSuccessful){
            response.body()?.let {
                kajianDetailResponse = it

                return Resource.Success(kajianDetailResponse ?: it)
            }
        }

        return Resource.Error(response.message())

    }

    fun getKajianAll(tanggal1: String, tanggal2: String) = viewModelScope.launch{
        safeKajianAll(tanggal1, tanggal2)
    }

    private suspend fun safeKajianAll(tanggal1: String, tanggal2: String) {
        homeMutable.value = Resource.Loading()
        if(hasInternetConnection()){
            try{
                coroutineScope {
                    val responseHariIni = async { homeRepository.getKajianHariIni(tanggal1, tanggal2) }
                    val responseAll = async { homeRepository.getKajianAll() }

                    val kajianHarIni = responseHariIni.await()
                    val kajianAll = responseAll.await()

                    val home = Home()


                    if (kajianHarIni.isSuccessful){
                        kajianHarIni.body()?.let {
                            home.listKajiaHariIni = it.data
                        }

                        if (kajianAll.isSuccessful){
                            kajianAll.body()?.let {
                                home.listKajiaAll = it.data

                                homeMutable.postValue(Resource.Success(home))
                            }
                        }else{

                            homeMutable.value = Resource.Error("${kajianHarIni?.message()}")
                        }

                    }else{

                        homeMutable.value = Resource.Error("${kajianHarIni?.message()}")
                    }

                }
            }catch (e: Exception){
                homeMutable.value = Resource.Error("${e.message}")
            }
        }else{
            homeMutable.value = Resource.Error("Tidak ada koneksi internet")
        }

    }

    private fun handleKajianAllResponse(response: Response<ResponseList<Kajian>>): Resource<ResponseList<Kajian>>? {
        if(response.isSuccessful){
            response.body()?.let {
                kajianAllRespopnse = it

                return Resource.Success(kajianAllRespopnse ?: it)
            }
        }

        return Resource.Error(response.message())

    }

    @SuppressLint("WrongConstant")
    private fun hasInternetConnection(): Boolean{
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val info = connectivityManager.allNetworkInfo
            if (info != null) {
                for (i in info.indices) {
                    if (info[i].state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
        }
        return false


    }

}