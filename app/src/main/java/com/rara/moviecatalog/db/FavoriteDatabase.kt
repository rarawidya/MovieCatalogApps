package com.rara.moviecatalog.db

data class FavoriteDatabase(
    val id: Long? = -1,
    val movieId: String,
    val movieType: String,
    val movieTitle: String,
    val posterPath: String
) {
    companion object {
        const val FAVORITE_MOVIE: String = "FAVORITE_MOVIE"
        const val ID: String = "ID_"
        const val MOVIE_ID: String = "MOVIE_ID"
        const val MOVIE_TYPE: String = "MOVIE_TYPE"
        const val MOVIE_TITLE: String = "MOVIE_TITLE"
        const val POSTER_PATH: String = "POSTER"
    }
}