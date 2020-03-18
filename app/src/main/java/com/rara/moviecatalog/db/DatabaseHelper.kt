package com.rara.moviecatalog.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.rara.moviecatalog.MainActivity
import okhttp3.internal.Internal.instance
import org.jetbrains.anko.db.*

class DatabaseHelper(context: Context, dbName: String = "favorite.db") :
    ManagedSQLiteOpenHelper(context, dbName, null, 1) {
    private lateinit var database: SQLiteDatabase

    companion object {

        private var instance: DatabaseHelper? = null
        const val AUTHORITY = "com.rara.moviecatalog"
        private const val SCHEME = "content"


        @Synchronized
        fun getInstance(context: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance as DatabaseHelper
        }

        val CONTENT_URI = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY).appendPath(FavoriteDatabase.FAVORITE_MOVIE).build()
    }

    fun open() {
        database = instance!!.writableDatabase
    }

    fun movieProvider(): Cursor {
        return database.query(
            FavoriteDatabase.FAVORITE_MOVIE,
            null, "${FavoriteDatabase.MOVIE_TYPE} = ?", arrayOf(MainActivity.MOVIE),
            null, null, null
        )
    }

    fun tvShowProvider(): Cursor {
        return database.query(
           FavoriteDatabase.FAVORITE_MOVIE,
            null, "${FavoriteDatabase.MOVIE_TYPE} = ?", arrayOf(MainActivity.TVSHOW),
            null,null, null
        )
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.createTable(
                FavoriteDatabase.FAVORITE_MOVIE, true,
                FavoriteDatabase.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteDatabase.MOVIE_ID to TEXT,
                FavoriteDatabase.MOVIE_TYPE to TEXT,
                FavoriteDatabase.MOVIE_TITLE to TEXT,
                FavoriteDatabase.POSTER_PATH to TEXT,
                FavoriteDatabase.OVERVIEW to TEXT
            )
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.dropTable(FavoriteDatabase.FAVORITE_MOVIE, true)
        }
    }
}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)
