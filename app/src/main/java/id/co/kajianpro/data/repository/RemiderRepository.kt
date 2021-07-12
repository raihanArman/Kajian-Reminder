package id.co.kajianpro.data.repository

import id.co.kajianpro.data.db.AppDatabase
import id.co.kajianpro.data.model.Kajian
import id.co.kajianpro.data.model.Waktu

class RemiderRepository (val appDatabase : AppDatabase){
    fun getAllKajianReminder(idUser: String) =
        appDatabase.databaseDao().getAllKajianReminder(idUser)

    fun checkKajianReminder(idKajian: String, idUser: String) =
        appDatabase.databaseDao().checkKajianReminder(idKajian, idUser)

    suspend fun insertKajianReminder(kajian: Kajian) =
        appDatabase.databaseDao().insertKajianReminder(kajian)

    suspend fun deleteKajianReminder(idKajian: String, idUser: String) =
        appDatabase.databaseDao().deleteKajianReminder(idKajian, idUser)

    fun getAllWaktu(idKajian: String) =
        appDatabase.databaseDao().getAllWaktu(idKajian)

    suspend fun insertWaktu(waktu: Waktu) =
        appDatabase.databaseDao().insertWaktu(waktu)

    suspend fun deleteWaktu(idKajian: String) =
        appDatabase.databaseDao().deleteWaktu(idKajian)

}