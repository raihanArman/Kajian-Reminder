package id.co.kajianpro.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id_user")
    @Expose
    var idUser: String ?= "",

    @SerializedName("username")
    @Expose
    var username: String ?= "",

    @SerializedName("email")
    @Expose
    var email: String ?= "",

    @SerializedName("nama")
    @Expose
    var nama: String ?= "",

    @SerializedName("no_telp")
    @Expose
    var no_telp: String ?= "",

    @SerializedName("alamat")
    @Expose
    var alamat: String ?= ""
)