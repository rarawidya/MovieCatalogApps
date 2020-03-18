package com.rara.moviecatalog.widget

import android.content.Intent
import android.widget.RemoteViewsService

class FavoriteWidgetService : RemoteViewsService(){
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackRemoteViewFactory(this.applicationContext)
    }

}