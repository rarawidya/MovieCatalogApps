package com.rara.moviecatalog.tvshow

import com.rara.moviecatalog.api.ApiRepository
import com.rara.moviecatalog.api.MovieApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowPresenter (
    private val tvShowInterface: TvShowInterface,
    private val movieApi: MovieApi)
{
    fun loadTvShow() {
        tvShowInterface.showLoading()
        movieApi.loadTvShow(ApiRepository.API_KEY).enqueue(object : Callback<TvShowResponseModel> {
            override fun onFailure(call: Call<TvShowResponseModel>, t: Throwable) {
                tvShowInterface.hideLoading()


            }

            override fun onResponse(
                call: Call<TvShowResponseModel>,
                response: Response<TvShowResponseModel>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null) {
                        tvShowInterface.tvShowData(data)
                    }
                    tvShowInterface.hideLoading()
                }

            }

        })
    }

    fun searchTvShow(tvShow: String) {
        tvShowInterface.showLoading()
        movieApi.searchTVShow(ApiRepository.API_KEY, tvShow)
            .enqueue(object : Callback<TvShowResponseModel> {
                override fun onFailure(call: Call<TvShowResponseModel>, t: Throwable) {
                    tvShowInterface.hideLoading()
                }

                override fun onResponse(
                    call: Call<TvShowResponseModel>,
                    response: Response<TvShowResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            tvShowInterface.tvShowData(data)
                        }
                        tvShowInterface.hideLoading()
                    }
                }

            })
    }

}
