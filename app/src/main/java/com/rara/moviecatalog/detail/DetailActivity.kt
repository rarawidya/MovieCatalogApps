package com.rara.moviecatalog.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.rara.moviecatalog.MainActivity
import com.rara.moviecatalog.R
import com.rara.moviecatalog.api.ApiRepository
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast

class DetailActivity : AppCompatActivity(), DetailInterface {

    private lateinit var detailPresenter: DetailPresenter
    private var type: String = "type"
    private var id: String = "0"
    private var title: String = "title"
    private var posterPath: String = "poster_path"
    private var overview: String = "overview"
    private var menuItem: Menu? = null
    private var isFavoriteDatabase: Boolean = false

    companion object {
        const val INTENT_RESULT_CODE = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getStringExtra(MainActivity.DATA_EXTRA)
        type = intent.getStringExtra(MainActivity.TYPE)

        val service = ApiRepository.create()
        detailPresenter = DetailPresenter(this, service)

        if (type == MainActivity.MOVIE) {
            detailPresenter.loadMovieDetail(id)
        } else if (type == MainActivity.TVSHOW) {
            detailPresenter.loadTvShowDetail(id)
        }
        isFavoriteDatabase = detailPresenter.setFavorite(this, id)
    }

    override fun showLoading() {
        progressBarDetail.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBarDetail.visibility = View.GONE
    }

    override fun showMovieDetail(movie: MovieDetailModel) {
        title = movie.title
        posterPath = movie.poster_path
        overview = movie.overview
        supportActionBar?.title = movie.title

        tvJudulFilm.text = movie.title
        tvSinopsis.text = movie.overview

        Glide.with(this).load(ApiRepository.BASE_IMAGE + movie.poster_path)
            .into(ivPoster)

        Glide.with(this).load(ApiRepository.BASE_IMAGE + movie.backdrop_path)
            .into(ivBackdrop)
    }

    override fun showTvShowDetail(tvShow: TvShowDetailModel) {
        title = tvShow.name
        posterPath = tvShow.poster_path
        overview = tvShow.overview
        supportActionBar?.title = tvShow.name

        tvJudulFilm.text = tvShow.name
        tvSinopsis.text = tvShow.overview

        Glide.with(this).load(ApiRepository.BASE_IMAGE + tvShow.poster_path)
            .into(ivPoster)

        Glide.with(this).load(ApiRepository.BASE_IMAGE + tvShow.backdrop_path)
            .into(ivBackdrop)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                onBackPressed()
                finish()
                true
            }
            R.id.favorite_movie -> {
                if (isFavoriteDatabase) {
                    detailPresenter.removeFavorite(this, id)
                    isFavoriteDatabase = !isFavoriteDatabase
                    setFavorite()
                } else {
                    if (id != "0" && title != "title" && type != "type" && posterPath != "poster_path") {
                        detailPresenter.addFavorite(this, id, type, title, posterPath, overview)
                        isFavoriteDatabase = !isFavoriteDatabase
                        setFavorite()
                    } else {
                        toast("Not Available")
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun setFavorite() {
        if (isFavoriteDatabase) {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        } else {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
        }
    }

    override fun onBackPressed() {
        setResult(INTENT_RESULT_CODE, Intent())
        super.onBackPressed()
    }
}
