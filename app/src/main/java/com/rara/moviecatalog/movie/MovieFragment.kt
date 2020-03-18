package com.rara.moviecatalog.movie


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rara.moviecatalog.MainActivity

import com.rara.moviecatalog.R
import com.rara.moviecatalog.api.ApiRepository
import com.rara.moviecatalog.detail.DetailActivity
import com.rara.moviecatalog.model.Movie
import kotlinx.android.synthetic.main.fragment_movie.*


class MovieFragment : Fragment(), MovieInterface {
    private lateinit var moviePresenter: MoviePresenter
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var recyclerView: RecyclerView
    private var listMovie = mutableListOf<Movie>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun showLoading() {
        progresBarMovie.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progresBarMovie.visibility = View.GONE
    }

    override fun movieData(movie: MovieResponseModel) {
        movieViewModel.setMovie(movie)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rvMovie)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        movieViewModel.getMovie().observe(this, getMovie)

        val services = ApiRepository.create()

        moviePresenter = MoviePresenter(this, services)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        movieAdapter = MovieAdapter(requireContext(), listMovie){
            val i = Intent(context, DetailActivity::class.java)
            i.putExtra(MainActivity.DATA_EXTRA, it.id)
            i.putExtra(MainActivity.TYPE, MainActivity.MOVIE)
            startActivity(i)
        }
        recyclerView.adapter = movieAdapter
        if (savedInstanceState == null) {
            moviePresenter.loadMovie()
        }else{
            hideLoading()
        }
        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(MainActivity.INSTANCE, MovieFragment::class.java.simpleName)
        super.onSaveInstanceState(outState)
    }

    private val getMovie = Observer<MovieResponseModel> {
       if (it != null){
           movieAdapter.setData(it.results)
       }
    }
}
