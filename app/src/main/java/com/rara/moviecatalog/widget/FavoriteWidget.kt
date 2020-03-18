package com.rara.moviecatalog.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.rara.moviecatalog.MainActivity
import com.rara.moviecatalog.R
import com.rara.moviecatalog.detail.DetailActivity

/**
 * Implementation of App Widget functionality.
 */
class FavoriteWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object{
        const val ACTION = "ACTION_FOR_WIDGET"
        const val MOVIE_ID = "MOVIE_ID"
        const val MOVIE_TYPE = "MOVIE_TYPE"

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, FavoriteWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            val remoteView = RemoteViews(context.packageName, R.layout.favorite_widget)
            remoteView.setRemoteAdapter(R.id.stack_View, intent)
            remoteView.setEmptyView(R.id.stack_View, R.id.empty_View)

            val widgetText =context.resources.getString(R.string.appwidget_text)
            remoteView.setTextViewText(R.string.appwidget_text, widgetText)

            val toastIntent = Intent(context, FavoriteWidget::class.java)
            toastIntent.action = ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            val pendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            remoteView.setPendingIntentTemplate(R.id.stack_View, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, remoteView)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent != null) {
            if (intent.action == ACTION){
                val id = intent.getStringExtra(MOVIE_ID)
                val type = intent.getStringExtra(MOVIE_TYPE)

                val i = Intent(context, DetailActivity::class.java)
                i.putExtra(MainActivity.DATA_EXTRA,id)
                i.putExtra(MainActivity.TYPE, type)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context?.startActivity(i)
            }
        }
    }
}
