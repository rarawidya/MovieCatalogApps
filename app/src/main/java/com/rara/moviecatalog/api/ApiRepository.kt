package com.rara.moviecatalog.api

import com.rara.moviecatalog.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepository {
    var BASE_URL: String = BuildConfig.BASE_URL
    var API_KEY: String = BuildConfig.API_KEY
    var BASE_IMAGE: String = BuildConfig.BASE_IMAGE

    fun create(): MovieApi {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(MovieApi::class.java)
    }
}