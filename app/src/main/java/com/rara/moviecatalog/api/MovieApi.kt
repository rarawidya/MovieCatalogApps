package com.rara.moviecatalog.api

import com.rara.moviecatalog.detail.MovieDetailModel
import com.rara.moviecatalog.detail.TvShowDetailModel
import com.rara.moviecatalog.movie.MovieResponseModel
import com.rara.moviecatalog.tvshow.TvShowResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    fun loadMovie (
        @Query("api_key") api_key: String,
        @Query("language") language: String  = "en-US"
    ): Call<MovieResponseModel>

    @GET("discover/tv")
    fun loadTvShow (
        @Query("api_key") api_key: String,
        @Query("language") language: String  = "en-US"
    ): Call<TvShowResponseModel>

    @GET("tv/{id}")
    fun loadTvShowDetail (
        @Path("id") id: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String = "en-US"
    ): Call<TvShowDetailModel>

    @GET("movie/{id}")
    fun loadMovieDetail (
        @Path("id") id: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String = "en-US"
    ): Call<MovieDetailModel>
}