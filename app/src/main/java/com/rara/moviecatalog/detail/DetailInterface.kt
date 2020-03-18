package com.rara.moviecatalog.detail

interface DetailInterface {
    fun showLoading()
    fun hideLoading()
    fun showMovieDetail(movie: MovieDetailModel)
    fun showTvShowDetail(tvShow: TvShowDetailModel)

}