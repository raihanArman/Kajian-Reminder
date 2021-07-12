package id.co.kajianpro.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.co.kajianpro.data.model.Kajian
import id.co.kajianpro.data.model.Waktu

@Dao
interface DatabaseDao {

    @Query("SELECT * FROM tb_kajian WHERE id_user = :iduser")
    fun getAllKajianReminder(iduser: String): List<Kajian>

    @Query("DELETE FROM tb_kajian WHERE id_kajian = :idKajian AND id_user = :idUser")
    suspend fun deleteKajianReminder(idKajian: String, idUser: String)

    @Query("SELECT EXISTS(SELECT * FROM tb_kajian WHERE id_kajian = :idKajian AND id_user = :idUser)")
    fun checkKajianReminder(idKajian: String, idUser: String): Boolean

    @Insert
    suspend fun insertKajianReminder(kajian: Kajian): Long

    @Insert
    suspend fun insertWaktu(waktu: Waktu): Long

    @Query("SELECT * FROM tb_waktu WHERE id_kajian = :idKajian")
    fun getAllWaktu(idKajian: String): List<Waktu>

    @Query("DELETE FROM tb_waktu WHERE id_kajian = :idKajian")
    suspend fun deleteWaktu(idKajian: String)

}