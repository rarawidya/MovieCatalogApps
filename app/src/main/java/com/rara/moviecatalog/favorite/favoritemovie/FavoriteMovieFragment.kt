package com.rara.moviecatalog.favorite.favoritemovie


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rara.moviecatalog.MainActivity

import com.rara.moviecatalog.R
import com.rara.moviecatalog.detail.DetailActivity
import com.rara.moviecatalog.detail.DetailActivity.Companion.INTENT_RESULT_CODE
import com.rara.moviecatalog.favorite.FavoriteFragment.Companion.INTENT_REQUEST_CODE
import com.rara.moviecatalog.model.Movie
import com.rara.moviecatalog.movie.MovieAdapter
import kotlinx.android.synthetic.main.fragment_favorite_movie.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMovieFragment : Fragment(), FavoriteMovieInterface {
    private var listMovie = mutableListOf<Movie>()
    private lateinit var favoriteMoviePresenter: FavoriteMoviePresenter
    private lateinit var favoriteMovieViewModel: FavoriteMovieViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel::class.java)
        favoriteMovieViewModel.getMovie().observe(this, getFavoriteMovie)
        favoriteMoviePresenter = FavoriteMoviePresenter(this)

        rv_favorite_Movie.addItemDecoration(
            DividerItemDecoration(
                rv_favorite_Movie.context,
                DividerItemDecoration.VERTICAL
            )
        )
        rv_favorite_Movie.layoutManager = LinearLayoutManager(context)

        movieAdapter = MovieAdapter(requireContext(), listMovie) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(MainActivity.DATA_EXTRA, it.id)
            intent.putExtra(MainActivity.TYPE, MainActivity.MOVIE)
            startActivityForResult(intent, INTENT_REQUEST_CODE)
        }
        rv_favorite_Movie.adapter = movieAdapter

        if (savedInstanceState == null) {
            favoriteMoviePresenter.getFavoriteMovie(requireContext())
        } else {
            hideLoading()
        }
        super.onActivityCreated(savedInstanceState)
    }

    private val getFavoriteMovie = Observer<List<Movie>> {
        if (it != null){
            movieAdapter.setData(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == INTENT_REQUEST_CODE) {
            if (resultCode == INTENT_RESULT_CODE) {
            favoriteMoviePresenter.getFavoriteMovie((requireContext()))
        }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showLoading() {
        progres_bar_favorite_Movie.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progres_bar_favorite_Movie.visibility = View.GONE
    }

    override fun favoriteMovie(movie: List<Movie>) {
        favoriteMovieViewModel.setMovie(movie)
        if (movie.isEmpty()) {
            tv_empty_movie.visibility = View.VISIBLE
        }
    }
}
