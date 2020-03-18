package com.rara.moviecatalog.detail

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.rara.moviecatalog.api.ApiRepository
import com.rara.moviecatalog.api.MovieApi
import com.rara.moviecatalog.db.FavoriteDatabase
import com.rara.moviecatalog.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter (
    private val detailInterface: DetailInterface,
    private val movieApi: MovieApi
){
    fun loadMovieDetail(id: String) {
        detailInterface.showLoading()
        movieApi.loadMovieDetail(id, ApiRepository.API_KEY)
            .enqueue(object : Callback<MovieDetailModel>{
                override fun onFailure(call: Call<MovieDetailModel>, t: Throwable) {
                    detailInterface.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieDetailModel>,
                    response: Response<MovieDetailModel>
                ) {
                    if (response.isSuccessful){
                        val data = response.body()
                        if(data != null){
                            detailInterface.showMovieDetail(data)
                        }
                        detailInterface.hideLoading()
                    }
                }

            })
    }

    fun loadTvShowDetail(id: String){
        detailInterface.showLoading()
        movieApi.loadTvShowDetail(id, ApiRepository.API_KEY)
            .enqueue(object : Callback<TvShowDetailModel>{
                override fun onFailure(call: Call<TvShowDetailModel>, t: Throwable) {
                    detailInterface.hideLoading()
                }

                override fun onResponse(
                    call: Call<TvShowDetailModel>,
                    response: Response<TvShowDetailModel>
                ) {
                    if(response.isSuccessful){
                        val data = response.body()
                        if(data != null){
                            detailInterface.showTvShowDetail(data)
                        }
                        detailInterface.hideLoading()
                    }
                }

            })
    }

    fun addFavorite(
        context: Context?,
        movieId: String,
        movieType: String,
        movieTitle: String,
        posterPath: String
    ) {
        try {
            context?.database?.use {
                insert(
                    FavoriteDatabase.FAVORITE_MOVIE,
                    FavoriteDatabase.MOVIE_ID to movieId,
                    FavoriteDatabase.MOVIE_TYPE to movieType,
                    FavoriteDatabase.MOVIE_TITLE to movieTitle,
                    FavoriteDatabase.POSTER_PATH to posterPath
                )
            }
            context?.toast("Added to Favorite")?.show()
        } catch (error: SQLiteConstraintException) {
            context?.toast(error.localizedMessage)?.show()
        }
    }

    fun removeFavorite(context: Context?, movieId: String){
        try {
            context?.database?.use {
                delete(FavoriteDatabase.FAVORITE_MOVIE, "(MOVIE_ID = {id})",
                    "id" to movieId)
            }
            context?.toast("Remove from Favorite")?.show()
        }catch (error: SQLiteConstraintException) {
            context?.toast(error.localizedMessage)?.show()
        }
    }

    fun setFavorite(context: Context?, movieId: String) : Boolean {
        var isFavoriteDatabase = false

        context?.database?.use {
            val result = select(FavoriteDatabase.FAVORITE_MOVIE)
                .whereArgs(
                    "(MOVIE_ID = {id})",
                    "id" to movieId
                )
            val favorite = result.parseList(classParser<FavoriteDatabase>())
            if (favorite.isNotEmpty()) {
                isFavoriteDatabase = true
            }
        }
        return isFavoriteDatabase
    }
}