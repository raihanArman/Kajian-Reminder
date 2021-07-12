package id.co.kajianpro.data.model

import com.google.gson.annotations.SerializedName

data class Value (
    @SerializedName("value")
    val value: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data_user")
    val user: User

)