package com.example.masakapa.data.remot

import android.content.Context
import com.example.masakapa.utils.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://themealdb.com/api/json/v1/1/"

    fun create(context: Context): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
