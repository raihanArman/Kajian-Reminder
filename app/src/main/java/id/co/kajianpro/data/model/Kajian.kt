package id.co.kajianpro.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(tableName = "tb_kajian")
data class Kajian (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int = 0,

    @ColumnInfo(name = "id_user")
    var idUser: String ?= "",

    @SerializedName("id_kajian")
    @ColumnInfo(name = "id_kajian")
    var idkajian: String ?= "",

    @SerializedName("judul_kajian")
    @ColumnInfo(name = "nama_kajian")
    var namaKajian: String ?= "",

    @SerializedName("id_kategori")
    @ColumnInfo(name = "id_kategori")
    var idKategori: String ?= "",

    @SerializedName("nama_kategori")
    @ColumnInfo(name = "nama_kategori")
    var namaKategori: String ?= "",

    @SerializedName("tanggal")
    var tanggal: Date?= Date(),

    @SerializedName("gambar")
    @ColumnInfo(name = "gambar")
    var gambar: String ?= "",

    @SerializedName("lokasi")
    @ColumnInfo(name = "tempat")
    var tempat: String ?= "",

    @SerializedName("catatan")
    var catatan: String ?= "",

    @SerializedName("link")
    var link: String ?= "",

    @SerializedName("pemateri")
    var pemateri: String ?= "",

    @ColumnInfo(name = "tanggal_kajian")
    var tanggalReminder: String ?= ""
): Serializable