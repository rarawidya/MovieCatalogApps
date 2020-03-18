package com.rara.moviecatalog.favorite.favoritetvshow


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
import com.rara.moviecatalog.model.TvShow
import com.rara.moviecatalog.tvshow.TvShowAdapter
import kotlinx.android.synthetic.main.fragment_favorite_tv_show.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTvShowFragment : Fragment(), FavoriteTvShowInterface {

    private var listTvShow = mutableListOf<TvShow>()
    private lateinit var favoriteTvShowPresenter: FavoriteTvShowPresenter
    private lateinit var favoriteTvShowViewModel: FavoriteTvShowViewModel
    private lateinit var tvShowAdapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        favoriteTvShowViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel::class.java)
        favoriteTvShowViewModel.getTvShow().observe(this, getFavoriteTvShow)
        favoriteTvShowPresenter = FavoriteTvShowPresenter(this)

        rv_favorite_TvShow.addItemDecoration(
            DividerItemDecoration(
                rv_favorite_TvShow.context,
                DividerItemDecoration.VERTICAL
            )
        )
        rv_favorite_TvShow.layoutManager = LinearLayoutManager(context)

        tvShowAdapter = TvShowAdapter(requireContext(), listTvShow) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(MainActivity.DATA_EXTRA, it.id)
            intent.putExtra(MainActivity.TYPE, MainActivity.TVSHOW)
            startActivityForResult(intent, INTENT_REQUEST_CODE)
        }
        rv_favorite_TvShow.adapter = tvShowAdapter

        if (savedInstanceState == null) {
            favoriteTvShowPresenter.getFavoriteTvShow(requireContext())
        } else {
            hideLoading()
        }
        super.onActivityCreated(savedInstanceState)
    }

    private val getFavoriteTvShow = Observer<List<TvShow>> {
        if (it != null){
            tvShowAdapter.setData(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == INTENT_REQUEST_CODE) {
            if (resultCode == INTENT_RESULT_CODE) {
                favoriteTvShowPresenter.getFavoriteTvShow((requireContext()))
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showLoading() {
        progres_bar_favorite_TvShow.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progres_bar_favorite_TvShow.visibility = View.GONE
    }

    override fun favoriteTvShow(tvShow: List<TvShow>) {
        favoriteTvShowViewModel.setTvShow(tvShow)
        if (tvShow.isEmpty()) {
            tv_empty_tvShow.visibility = View.VISIBLE
        }
    }
}
