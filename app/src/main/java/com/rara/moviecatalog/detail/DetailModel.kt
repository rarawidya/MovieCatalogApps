package com.rara.moviecatalog.detail

data class MovieDetailModel(
    val title: String,
    val poster_path: String,
    val overview: String
)

data class TvShowDetailModel(
    val name: String,
    val overview: String,
    val poster_path: String
)