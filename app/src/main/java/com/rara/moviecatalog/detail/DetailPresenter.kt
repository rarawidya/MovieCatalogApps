package com.rara.moviecatalog.detail

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.os.Build
import com.rara.moviecatalog.api.ApiRepository
import com.rara.moviecatalog.api.MovieApi
import com.rara.moviecatalog.db.FavoriteDatabase
import com.rara.moviecatalog.db.database
import com.rara.moviecatalog.widget.WidgetService
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter(
    private val detailInterface: DetailInterface,
    private val movieApi: MovieApi
) {
    fun loadMovieDetail(id: String) {
        detailInterface.showLoading()
        movieApi.loadMovieDetail(id, ApiRepository.API_KEY)
            .enqueue(object : Callback<MovieDetailModel> {
                override fun onFailure(call: Call<MovieDetailModel>, t: Throwable) {
                    detailInterface.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieDetailModel>,
                    response: Response<MovieDetailModel>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            detailInterface.showMovieDetail(data)
                        }
                        detailInterface.hideLoading()
                    }
                }

            })
    }

    fun loadTvShowDetail(id: String) {
        detailInterface.showLoading()
        movieApi.loadTvShowDetail(id, ApiRepository.API_KEY)
            .enqueue(object : Callback<TvShowDetailModel> {
                override fun onFailure(call: Call<TvShowDetailModel>, t: Throwable) {
                    detailInterface.hideLoading()
                }

                override fun onResponse(
                    call: Call<TvShowDetailModel>,
                    response: Response<TvShowDetailModel>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            detailInterface.showTvShowDetail(data)
                        }
                        detailInterface.hideLoading()
                    }
                }

            })
    }

    fun addFavorite(
        context: Context,
        movieId: String,
        movieType: String,
        movieTitle: String,
        posterPath: String,
        overview: String
    ) {
        try {
            context.database.use {
                insert(
                    FavoriteDatabase.FAVORITE_MOVIE,
                    FavoriteDatabase.MOVIE_ID to movieId,
                    FavoriteDatabase.MOVIE_TYPE to movieType,
                    FavoriteDatabase.MOVIE_TITLE to movieTitle,
                    FavoriteDatabase.POSTER_PATH to posterPath,
                    FavoriteDatabase.OVERVIEW to overview
                )
            }
            context.toast("Added to Favorite").show()
            updateWidget(context)
        } catch (error: SQLiteConstraintException) {
            context.toast(error.localizedMessage).show()
        }
    }

    fun removeFavorite(context: Context, movieId: String) {
        try {
            context.database.use {
                delete(
                    FavoriteDatabase.FAVORITE_MOVIE, "(MOVIE_ID = {id})",
                    "id" to movieId
                )
            }
            context?.toast("Remove from Favorite")?.show()

            updateWidget(context)


        } catch (error: SQLiteConstraintException) {
            context.toast(error.localizedMessage).show()
        }
    }

    fun setFavorite(context: Context, movieId: String): Boolean {
        var isFavoriteDatabase = false

        context.database.use {
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

    private fun updateWidget(context: Context) {
        val jobId = (0 until 200).random()
        val mils: Long = 10000
        val component = ComponentName(context, WidgetService::class.java)
        val builder = JobInfo.Builder(jobId, component)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setMinimumLatency(mils)
        } else {
            builder.setPeriodic(mils)
        }
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(builder.build())
    }
}