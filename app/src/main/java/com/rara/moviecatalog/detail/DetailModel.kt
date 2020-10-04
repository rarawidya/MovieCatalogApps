package com.rara.moviecatalog.detail

data class MovieDetailModel(
    val title: String,
    val poster_path: String,
    val overview: String,
    val backdrop_path: String,
    val release_date: String,
    val runtime: String
)

data class TvShowDetailModel(
    val name: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val first_air_date : String,
    val episode_run_time: List<Int>
)