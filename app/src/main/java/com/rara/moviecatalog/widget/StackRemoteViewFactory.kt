package com.rara.moviecatalog.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rara.moviecatalog.R
import com.rara.moviecatalog.api.ApiRepository
import com.rara.moviecatalog.db.FavoriteDatabase
import com.rara.moviecatalog.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import java.util.concurrent.ExecutionException

class StackRemoteViewFactory (private val context: Context) :
        RemoteViewsService.RemoteViewsFactory {
    private val data = mutableListOf<WidgetModel>()
    override fun onCreate() {

    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long = 0


    override fun onDataSetChanged() {
       loadMovie()
    }
    private fun loadMovie() {
        context.database.use {
            data.clear()
            val result = select(FavoriteDatabase.FAVORITE_MOVIE)
            val favorite = result.parseList(classParser<FavoriteDatabase>())
            for (i in 0 until favorite.size) {
                data.add(
                    WidgetModel(
                        favorite[i].movieId,
                        favorite[i].posterPath,
                        favorite[i].movieType
                    )
                )
            }
        }
    }

    override fun hasStableIds(): Boolean {
       return false
    }

    override fun getViewAt(position: Int): RemoteViews {
       val remoteView = RemoteViews(context.packageName, R.layout.widget_item)

        try {
            val poster =  Glide.with(context).asBitmap().load(ApiRepository.BASE_IMAGE + data[position].posterPath)
                .apply(RequestOptions().centerCrop())
                .submit()
                .get()
            remoteView.setImageViewBitmap(R.id.iv_Widget, poster)
        }catch (error: InterruptedException) {
            error.printStackTrace()
        }catch (error: ExecutionException) {
            error.printStackTrace()
        }

        val bundle = Bundle()
        bundle.putString(FavoriteWidget.MOVIE_ID, data[position].id)
        bundle.putString(FavoriteWidget.MOVIE_TYPE, data[position].type)
        val intent = Intent()
        intent.putExtras(bundle)

        remoteView.setOnClickFillInIntent(R.id.iv_Widget, intent)
        return remoteView
    }

    override fun getCount(): Int = data.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }


}