package com.rara.moviecatalog.favorite.favoritetvshow

import android.content.Context
import com.rara.moviecatalog.MainActivity
import com.rara.moviecatalog.db.FavoriteDatabase
import com.rara.moviecatalog.db.database
import com.rara.moviecatalog.model.TvShow
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteTvShowPresenter (private val favoriteTvShowInterface: FavoriteTvShowInterface) {
    private var listTvShow = mutableListOf<TvShow>()

    fun getFavoriteTvShow(context: Context) {
        favoriteTvShowInterface.showLoading()

        context?.database?.use {
            listTvShow.clear()

            val result = select(FavoriteDatabase.FAVORITE_MOVIE)
                .whereArgs("${FavoriteDatabase.MOVIE_TYPE} = '${MainActivity.TVSHOW}'")
            val favorite = result.parseList(classParser<FavoriteDatabase>())
            for (i in 0 until favorite.size) {
                listTvShow.add(
                    TvShow(
                        favorite[i].movieId,
                        favorite[i].movieTitle,
                        favorite[i].posterPath
                    )
                )
            }
        }
        favoriteTvShowInterface.hideLoading()
        favoriteTvShowInterface.favoriteTvShow(listTvShow)
    }
}