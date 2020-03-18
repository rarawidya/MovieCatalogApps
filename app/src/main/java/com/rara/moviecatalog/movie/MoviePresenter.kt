package com.rara.moviecatalog.movie

import com.rara.moviecatalog.api.ApiRepository
import com.rara.moviecatalog.api.MovieApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoviePresenter (
    private val movieInterface: MovieInterface,
    private val movieApi: MovieApi)
{
    fun loadMovie() {
        movieInterface.showLoading()
        movieApi.loadMovie(ApiRepository.API_KEY).enqueue(object : Callback<MovieResponseModel> {
            override fun onFailure(call: Call<MovieResponseModel>, t: Throwable) {
                movieInterface.hideLoading()


            }

            override fun onResponse(
                call: Call<MovieResponseModel>,
                response: Response<MovieResponseModel>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    data?.let { movieInterface.movieData(it) }
                    movieInterface.hideLoading()
                }

            }

        })
    }

}