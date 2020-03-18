package com.rara.moviecatalog.tvshow


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
import com.rara.moviecatalog.model.TvShow
import kotlinx.android.synthetic.main.fragment_tv_show.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment(), TvShowInterface {
    private lateinit var tvShowPresenter: TvShowPresenter
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var recyclerView: RecyclerView
    private var listTvShow = mutableListOf<TvShow>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rvTvShow)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)
        tvShowViewModel.getTvShow().observe(this, getTvShow)

        val services = ApiRepository.create()

        tvShowPresenter = TvShowPresenter(this, services)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        tvShowAdapter = TvShowAdapter(requireContext(), listTvShow){
            val i = Intent(context, DetailActivity::class.java)
            i.putExtra(MainActivity.DATA_EXTRA, it.id)
            i.putExtra(MainActivity.TYPE, MainActivity.TVSHOW)
            startActivity(i)
        }
        recyclerView.adapter = tvShowAdapter
        if (savedInstanceState == null) {
            tvShowPresenter.loadTvShow()
        }else{
            hideLoading()
        }
        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(MainActivity.INSTANCE, TvShowFragment::class.java.simpleName)
        super.onSaveInstanceState(outState)
    }

    override fun showLoading() {
        progresBarTvShow.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progresBarTvShow.visibility = View.GONE
    }

    override fun tvShowData(tvShow: TvShowResponseModel) {
        tvShowViewModel.setTvShow(tvShow)
    }

    private val getTvShow = Observer<TvShowResponseModel> {
        if (it != null){
            tvShowAdapter.setData(it.results)
        }
    }


}
