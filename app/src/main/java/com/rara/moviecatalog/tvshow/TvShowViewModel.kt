package com.rara.moviecatalog.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TvShowViewModel: ViewModel(){
    private var tvShows = MutableLiveData<TvShowResponseModel>()

    fun getTvShow() : LiveData<TvShowResponseModel> {
        return tvShows
    }
    fun setTvShow(tvShow: TvShowResponseModel){
        tvShows.postValue(tvShow)
    }

}