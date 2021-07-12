package id.co.dhanapps.data.api

import com.google.gson.GsonBuilder
import id.co.kajianpro.BuildConfig
import id.co.kajianpro.data.remote.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class RetrofitRequest {
    companion object{
        private val retrofit by lazy{
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

            val gson = GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd HH:mm")
                .create()

            Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build()
        }
        private val retrofitMaps by lazy{
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(ApiService::class.java)
        }

        val apiMaps by lazy {
            retrofitMaps.create(ApiService::class.java)
        }

    }
}