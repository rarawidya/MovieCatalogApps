package com.rara.moviecatalog.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.rara.moviecatalog.MainActivity
import com.rara.moviecatalog.R
import com.rara.moviecatalog.api.ApiRepository
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailInterface {

    private lateinit var detailPresenter: DetailPresenter
    private lateinit var view: View
    private var type: String = "type"
    private var id: String = "0"

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
    }

    override fun showLoading() {
        progressBarDetail.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBarDetail.visibility = View.GONE
    }

    override fun showMovieDetail(movie: MovieDetailModel) {
        supportActionBar?.title = movie.title

        tvJudulFilm.text = movie.title
        tvSinopsis.text = movie.overview

        Glide.with(this).load(ApiRepository.BASE_IMAGE + movie.poster_path)
            .into(ivPoster)
    }

    override fun showTvShowDetail(tvShow: TvShowDetailModel) {
        supportActionBar?.title = tvShow.name

        tvJudulFilm.text = tvShow.name
        tvSinopsis.text = tvShow.overview

        Glide.with(this).load(ApiRepository.BASE_IMAGE + tvShow.poster_path)
            .into(ivPoster)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                onBackPressed()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onBackPressed() {
        setResult(INTENT_RESULT_CODE, Intent())
        super.onBackPressed()
    }
}
