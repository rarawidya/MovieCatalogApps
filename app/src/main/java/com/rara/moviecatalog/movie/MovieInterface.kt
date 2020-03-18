package com.rara.moviecatalog.movie

interface MovieInterface {
    fun showLoading()
    fun hideLoading()
    fun movieData(
        movie: MovieResponseModel
    )
}