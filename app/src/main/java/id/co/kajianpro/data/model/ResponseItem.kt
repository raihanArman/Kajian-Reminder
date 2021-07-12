package id.co.kajianpro.data.model

import com.google.gson.annotations.SerializedName

data class ResponseItem<T> (
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: T
)