package com.rara.moviecatalog.favorite.favoritemovie

import android.content.Context
import com.rara.moviecatalog.MainActivity
import com.rara.moviecatalog.db.FavoriteDatabase
import com.rara.moviecatalog.db.database
import com.rara.moviecatalog.model.Movie
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteMoviePresenter (private val favoriteMovieInterface: FavoriteMovieInterface) {
    private  var listMovie = mutableListOf<Movie>()

    fun getFavoriteMovie(context: Context?) {
        favoriteMovieInterface.showLoading()

        context?.database?.use {
            listMovie.clear()

            val result = select(FavoriteDatabase.FAVORITE_MOVIE)
                .whereArgs("${FavoriteDatabase.MOVIE_TYPE} = '${MainActivity.MOVIE}'")
            val favorite = result.parseList(classParser<FavoriteDatabase>())
            for (i in 0 until favorite.size) {
                listMovie.add(Movie(
                        favorite[i].movieId,
                        favorite[i].movieTitle,
                        favorite[i].posterPath
                    )
                )
            }
        }
        favoriteMovieInterface.hideLoading()
        favoriteMovieInterface.favoriteMovie(listMovie)
        }
    }
