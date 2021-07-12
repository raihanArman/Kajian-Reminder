package id.co.kajianpro.data.remote

import id.co.dhanapps.data.*
import id.co.kajianpro.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login_user.php")
    suspend fun loginUser(
        @Field("email") username: String,
        @Field("password") password: String
    ) : Response<Value>


    @FormUrlEncoded
    @POST("registrasi_user.php")
    suspend fun registerUser(
            @Field("email") username: String,
            @Field("password") password: String,
            @Field("nama") nama: String
    ) : Response<Value>


    @GET("tampil_all_kajian.php")
    suspend fun getKajianAll(): Response<ResponseList<Kajian>>

    @GET("tampil_detail_kajian.php")
    suspend fun getKajianDetail(
            @Query("id_kajian") idKajian: String
    ): Response<ResponseItem<Kajian>>

    @GET("tampil_kajian_by_day.php")
    suspend fun getKajianByDay(
        @Query("date_start") dateStart: String,
        @Query("date_end") dateEnd: String
    ): Response<ResponseList<Kajian>>

    @GET("tampil_user.php")
    suspend fun getUserById(
        @Query("id_user") idUser: String
    ): Response<ResponseItem<User>>

}