package com.rara.moviecatalog.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseHelper (context: Context, dbName:String = "favorite.db" ) :
        ManagedSQLiteOpenHelper(context, dbName, null, 1) {
    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(context: Context) : DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.createTable(
              FavoriteDatabase.FAVORITE_MOVIE, true,
                FavoriteDatabase.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteDatabase.MOVIE_ID to TEXT,
                FavoriteDatabase.MOVIE_TYPE to TEXT,
                FavoriteDatabase.MOVIE_TITLE to TEXT,
                FavoriteDatabase.POSTER_PATH to TEXT
            )
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db !=null) {
            db.dropTable(FavoriteDatabase.FAVORITE_MOVIE, true)
        }
    }
}
val Context.database: DatabaseHelper
get() = DatabaseHelper.getInstance(applicationContext)
