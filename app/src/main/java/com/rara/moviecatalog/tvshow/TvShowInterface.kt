package com.rara.moviecatalog.tvshow

interface TvShowInterface {
    fun showLoading()
    fun hideLoading()
    fun tvShowData(
        tvShow: TvShowResponseModel
    )
}