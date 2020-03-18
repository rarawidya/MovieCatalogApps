package com.rara.moviecatalog.detail

import com.rara.moviecatalog.api.ApiRepository
import com.rara.moviecatalog.api.MovieApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter (
    private val detailInterface: DetailInterface,
    private val movieApi: MovieApi
){
    fun loadMovieDetail(id: String) {
        detailInterface.showLoading()
        movieApi.loadMovieDetail(id, ApiRepository.API_KEY)
            .enqueue(object : Callback<MovieDetailModel>{
                override fun onFailure(call: Call<MovieDetailModel>, t: Throwable) {
                    detailInterface.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieDetailModel>,
                    response: Response<MovieDetailModel>
                ) {
                    if (response.isSuccessful){
                        val data = response.body()
                        if(data != null){
                            detailInterface.showMovieDetail(data)
                        }
                        detailInterface.hideLoading()
                    }
                }

            })
    }

    fun loadTvShowDetail(id: String){
        detailInterface.showLoading()
        movieApi.loadTvShowDetail(id, ApiRepository.API_KEY)
            .enqueue(object : Callback<TvShowDetailModel>{
                override fun onFailure(call: Call<TvShowDetailModel>, t: Throwable) {
                    detailInterface.hideLoading()
                }

                override fun onResponse(
                    call: Call<TvShowDetailModel>,
                    response: Response<TvShowDetailModel>
                ) {
                    if(response.isSuccessful){
                        val data = response.body()
                        if(data != null){
                            detailInterface.showTvShowDetail(data)
                        }
                        detailInterface.hideLoading()
                    }
                }

            })
    }
}