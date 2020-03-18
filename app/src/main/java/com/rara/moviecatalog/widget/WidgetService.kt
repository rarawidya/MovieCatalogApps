package com.rara.moviecatalog.widget

import android.app.job.JobParameters
import android.app.job.JobService
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.widget.RemoteViews
import com.rara.moviecatalog.R

class WidgetService : JobService() {
    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        val widgetManager = AppWidgetManager.getInstance(this)
        val remoteView = RemoteViews(packageName, R.layout.favorite_widget)
        val widgetFavorite = ComponentName(this, FavoriteWidget::class.java)
        widgetManager.updateAppWidget(widgetFavorite, remoteView)
        jobFinished(params, false)
        return true
    }

}