package com.rara.moviecatalog.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.rara.moviecatalog.db.DatabaseHelper
import com.rara.moviecatalog.db.DatabaseHelper.Companion.AUTHORITY
import com.rara.moviecatalog.db.FavoriteDatabase.Companion.FAVORITE_MOVIE
import com.rara.moviecatalog.db.database

class Provider : ContentProvider() {

    private lateinit var databaseHelper: DatabaseHelper
    private val movieId = 1
    private val tvShowId = 2

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        this.addURI(AUTHORITY, FAVORITE_MOVIE, movieId)
        this.addURI(AUTHORITY, FAVORITE_MOVIE, tvShowId)
    }
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        databaseHelper.open()
        return when (uriMatcher.match(uri)) {
            movieId -> databaseHelper.movieProvider()
            tvShowId -> databaseHelper.tvShowProvider()
            else -> databaseHelper.movieProvider()
        }
    }

    override fun onCreate(): Boolean {
        databaseHelper = context?.database!!
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
    return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

}