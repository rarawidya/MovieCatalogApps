package com.rara.moviecatalog.favorite.favoritemovie

import com.rara.moviecatalog.model.Movie

interface FavoriteMovieInterface {
    fun showLoading()
    fun hideLoading()
    fun favoriteMovie(movie: List<Movie>)
}
