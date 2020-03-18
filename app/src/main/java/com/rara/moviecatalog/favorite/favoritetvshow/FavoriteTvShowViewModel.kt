package com.rara.moviecatalog.favorite.favoritetvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rara.moviecatalog.model.TvShow

class FavoriteTvShowViewModel : ViewModel(){
    private var favoriteTvShow = MutableLiveData<List<TvShow>>()

    fun setTvShow(tvShow: List<TvShow>) {
        favoriteTvShow.postValue(tvShow)
    }

    fun getTvShow() : LiveData<List<TvShow>> {
        return favoriteTvShow
    }
}