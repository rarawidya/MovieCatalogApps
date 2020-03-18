package com.rara.moviecatalog.favorite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rara.moviecatalog.MainActivity.Companion.INSTANCE

import com.rara.moviecatalog.R
import com.rara.moviecatalog.favorite.favoritemovie.FavoriteMovieFragment
import com.rara.moviecatalog.favorite.favoritetvshow.FavoriteTvShowFragment
import com.rara.moviecatalog.util.ViewPagerAdapter

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    companion object {
        const val INTENT_REQUEST_CODE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = view.findViewById(R.id.view_pager_favorite)
        tabLayout = view.findViewById(R.id.t_layout_favorite)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        pagerAdapter = ViewPagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(FavoriteMovieFragment())
        pagerAdapter.addFragment(FavoriteTvShowFragment())
        viewPager.adapter = pagerAdapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(tabLayoutListener)
        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(INSTANCE, FavoriteFragment::class.java.simpleName)
        super.onSaveInstanceState(outState)
    }

    private val tabLayoutListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabSelected(tab: TabLayout.Tab) {
            viewPager.currentItem = tab.position
        }

    }
}
