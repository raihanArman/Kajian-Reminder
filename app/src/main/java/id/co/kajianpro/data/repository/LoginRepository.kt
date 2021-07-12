package id.co.kajianpro.data.repository

import id.co.dhanapps.data.api.RetrofitRequest

class LoginRepository {

    suspend fun getLoginUser(username: String, password: String) =
        RetrofitRequest.api.loginUser(username, password)

    suspend fun getRegisterUser(username: String, password: String, nama: String) =
            RetrofitRequest.api.registerUser(username, password, nama)

}