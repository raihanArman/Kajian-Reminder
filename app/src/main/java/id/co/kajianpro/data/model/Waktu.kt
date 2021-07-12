package id.co.kajianpro.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_waktu")
data class Waktu (
    @ColumnInfo(name = "id_kajian")
    var idKajian: String? = null,

    @ColumnInfo(name = "id_time")
    var idTime: String? = null
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int = 0
}