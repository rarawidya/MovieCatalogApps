package com.rara.moviecatalog

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.rara.moviecatalog.favorite.FavoriteFragment
import com.rara.moviecatalog.movie.MovieFragment
import com.rara.moviecatalog.setting.SettingsActivity
import com.rara.moviecatalog.tvshow.TvShowFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val DATA_EXTRA = "data"
        const val MOVIE = "movie"
        const val TVSHOW = "tvShow"
        const val TYPE = "type"
        const val INSTANCE = "instance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setOnNavigationItemReselectedListener { item ->
            when (item.itemId){
                R.id.navigation_movie -> {
                    val fragment = MovieFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.flContent, fragment, MovieFragment::class.java.simpleName)
                        .commit()
                    supportActionBar?.setTitle(R.string.title_movie)
                }
                R.id.navigation_tvshow -> {
                    val fragment = TvShowFragment ()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.flContent, fragment, TvShowFragment::class.java.simpleName)
                        .commit()
                    supportActionBar?.setTitle(R.string.title_tv)
                }
                R.id.navigation_favorite -> {
                    val fragment = FavoriteFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flContent, fragment, FavoriteFragment::class.java.simpleName)
                        .commit()

                    supportActionBar?.setTitle(R.string.favorite)
                }
            }
            true
        }
        if (savedInstanceState == null) {
            nav_view.selectedItemId = R.id.navigation_movie
        }else{
            when (savedInstanceState.getString(INSTANCE)) {
                MovieFragment::class.java.simpleName -> {
                    nav_view.selectedItemId = R.id.navigation_movie
                }
                TvShowFragment::class.java.simpleName -> {
                    nav_view.selectedItemId = R.id.navigation_tvshow
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.language_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
//                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                val mIntent = Intent(this, SettingsActivity::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
        super.onBackPressed()
    }
}
