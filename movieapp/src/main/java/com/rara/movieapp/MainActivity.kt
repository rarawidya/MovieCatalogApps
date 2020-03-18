package com.rara.movieapp

import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rara.movieapp.adapter.MovieAdapter
import com.rara.movieapp.db.DatabaseHelper
import com.rara.movieapp.db.FavoriteDatabase
import com.rara.movieapp.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), LoadMoviesCallback {

    companion object {
        const val MOVIE = "movie"
        const val TVSHOW = "tvShow"
    }

    private var movies = mutableListOf<Movie>()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var dataObserver: DataObserver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_movieApp.addItemDecoration(
            DividerItemDecoration(
                rv_movieApp.context, DividerItemDecoration.VERTICAL
            )
        )

        rv_movieApp.layoutManager = LinearLayoutManager(this)
        movieAdapter = MovieAdapter(this, movies)
        rv_movieApp.adapter = movieAdapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        dataObserver = DataObserver(handler, this)
        contentResolver.registerContentObserver(DatabaseHelper.CONTENT_URI, true, dataObserver)
        GetData(this, this).execute()
    }

    override fun postExecute(cursor: Cursor) {
        val cursorData = mapCursorToArrayList(cursor)
        if (cursorData.isNotEmpty()) {
            movies.addAll(cursorData)
            movieAdapter.notifyDataSetChanged()
        } else {
            tvEmpty.visibility = View.VISIBLE
        }
    }

    internal class DataObserver(handler: Handler, private val context: Context) :
        ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            GetData(context, context as MainActivity).execute()
        }
    }

    internal class GetData(context: Context, callback: LoadMoviesCallback) :
        AsyncTask<Void, Void, Cursor>() {
        private val weakContent = WeakReference(context)
        private val weakCallback = WeakReference(callback)

        override fun doInBackground(vararg params: Void?): Cursor? {
            return weakContent.get()?.contentResolver?.query(
                DatabaseHelper.CONTENT_URI, null, "${FavoriteDatabase.MOVIE_TYPE} = ?",
                arrayOf(MOVIE), null
            )
        }

        override fun onPostExecute(result: Cursor?) {
            super.onPostExecute(result)
            if (result != null) {
                weakCallback.get()?.postExecute(result)
            }
        }
    }
}

interface LoadMoviesCallback {
    fun postExecute(cursor: Cursor)
}

fun mapCursorToArrayList(cursor: Cursor): List<Movie> {
    val movie = mutableListOf<Movie>()

    while (cursor.moveToNext()) {
        val id = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDatabase.MOVIE_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDatabase.MOVIE_TITLE))
        val poster_path = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDatabase.POSTER_PATH))
        val overview = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDatabase.OVERVIEW))
        movie.add(Movie(id, title, poster_path, overview))
    }
    return movie
}
