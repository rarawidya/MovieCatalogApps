package com.rara.moviecatalog.favorite.favoritemovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rara.moviecatalog.model.Movie

class FavoriteMovieViewModel : ViewModel(){
    private var favoriteMovie = MutableLiveData<List<Movie>>()

    fun setMovie(movie: List<Movie>) {
        favoriteMovie.postValue(movie)
    }

    fun getMovie() : LiveData<List<Movie>> {
        return favoriteMovie
    }
}