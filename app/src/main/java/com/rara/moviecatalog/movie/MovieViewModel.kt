package com.rara.moviecatalog.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel: ViewModel(){
    private var movies = MutableLiveData<MovieResponseModel>()

    fun getMovie() : LiveData<MovieResponseModel>{
        return movies
    }
    fun setMovie(movie: MovieResponseModel){
        movies.postValue(movie)
    }

}