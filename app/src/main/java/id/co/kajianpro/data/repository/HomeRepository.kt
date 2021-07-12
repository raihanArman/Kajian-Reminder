package id.co.kajianpro.data.repository

import id.co.dhanapps.data.api.RetrofitRequest

class HomeRepository {
    suspend fun getKajianAll() =
            RetrofitRequest.api.getKajianAll()

    suspend fun getKajianDetail(idKajian: String) =
            RetrofitRequest.api.getKajianDetail(idKajian)

    suspend fun getKajianHariIni(tanggal1: String, tanggal2: String) =
        RetrofitRequest.api.getKajianByDay(tanggal1, tanggal2)

    suspend fun getProfilUserById(idUser: String) =
        RetrofitRequest.api.getUserById(idUser)
}