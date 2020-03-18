package com.rara.moviecatalog.favorite.favoritetvshow

import com.rara.moviecatalog.model.TvShow

interface FavoriteTvShowInterface {
    fun showLoading()
    fun hideLoading()
    fun favoriteTvShow(tvShow: List<TvShow>)
}