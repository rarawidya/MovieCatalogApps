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

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<MovieResponseModel>

    @GET("search/tv")
    fun searchTVShow(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<TvShowResponseModel>

    @GET("discover/movie")
    fun todayReleaseMovie(
        @Query("api_key") apiKey: String,
        @Query("primary_release_date.gte") primaryReleaseDateGte: String,
        @Query("primary_release_date.lte") primaryReleaseDateLte: String
    ): Call<MovieResponseModel>
}