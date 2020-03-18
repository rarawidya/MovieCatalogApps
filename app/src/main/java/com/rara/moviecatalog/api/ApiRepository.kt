package com.rara.moviecatalog.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepository {
    var BASE_URL: String = "https://api.themoviedb.org/3/"
    var API_KEY: String = "7725910d17b70668280d8bdcadb47a41"
    var BASE_IMAGE: String = "https://image.tmdb.org/t/p/w185/"

    fun create(): MovieApi {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(MovieApi::class.java)
    }
}