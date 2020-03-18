package com.rara.moviecatalog.setting

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.rara.moviecatalog.MainActivity
import com.rara.moviecatalog.R
import com.rara.moviecatalog.api.ApiRepository
import com.rara.moviecatalog.detail.DetailActivity
import com.rara.moviecatalog.movie.MovieResponseModel
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ReminderReceiver : BroadcastReceiver() {

    private val intentKey = "KEY"

    companion object {
        const val DAILY = 100
        const val RELEASE_DATE = 101
    }

    override fun onReceive(context: Context, intent: Intent?) {
      if (intent?.extras?.getInt(intentKey) == DAILY) {
          sendDailyNotification(context)
      } else if (intent?.extras?.getInt(intentKey) == RELEASE_DATE) {
          loadMovie(context)
      }
    }

    private fun loadMovie(context: Context) {
        val patternDate = "yyy-MM-dd"
        val today = SimpleDateFormat(patternDate).format(Date()).toString()

        val service = ApiRepository.create()
        service.todayReleaseMovie(ApiRepository.API_KEY, today, today).enqueue(object : Callback<MovieResponseModel>{
            override fun onFailure(call: Call<MovieResponseModel>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<MovieResponseModel>,
                response: Response<MovieResponseModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        processNotification(context, data)
                    }
                }
            }
        })
    }

    fun setRepeatingAlarm(context: Context?, type: Int) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (type == DAILY) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 7)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            val intent = Intent(context, ReminderReceiver::class.java)
            intent.putExtra(intentKey, DAILY)
            val pendingIntent = PendingIntent.getBroadcast(context, DAILY, intent, 0 )

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingIntent
            )
            context.toast(context.resources.getString(R.string.daily_subscribed))
        } else if (type == RELEASE_DATE) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 8)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            val intent = Intent(context, ReminderReceiver::class.java)
            intent.putExtra(intentKey, RELEASE_DATE)
            val pendingIntent = PendingIntent.getBroadcast(context, RELEASE_DATE, intent, 0)

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingIntent
            )
            context.toast(context.resources.getString(R.string.release_today_subscribed))
        }
    }

    fun stopRepeatingAlarm(context: Context?, type: Int) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, type, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        if (type == DAILY) {
            context.toast(context.resources.getString(R.string.daily_unsubcsribed))
        } else if (type == RELEASE_DATE){
            context.toast(context.resources.getString(R.string.release_today_unsubscribed))
        }
    }

    private fun processNotification(context: Context, data: MovieResponseModel) {
        var notificationId = 0
        for (i in notificationId until data.results.size) {
            sendReleaseTodayNotification(
                context, notificationId, data.results[i].id,
                data.results[i].title, data.results[i].overview
            )
            notificationId++
        }

    }

    private fun sendReleaseTodayNotification(
        context: Context,
        notificationId: Int,
        movieId: String,
        title: String,
        overview: String
    ) {
        val channelId = context.resources.getString(R.string.release_today_notification_channel_id)
        val channelName = context.getString(R.string.release_today_notification_channel_name)

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(MainActivity.DATA_EXTRA, movieId)
        intent.putExtra(MainActivity.TYPE, MainActivity.MOVIE)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val builder: NotificationCompat.Builder
        builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle(context.resources.getString(R.string.nowplaying) + title)
            .setContentText(overview)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notificationId, notification)
    }

    private fun sendDailyNotification(context: Context) {
        val channelId = context.resources.getString(R.string.daily_notification_channel_id)
        val channelName = context.resources.getString(R.string.daily_notification_channel_name)

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(
            context, 0,intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val builder: NotificationCompat.Builder
        builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle(context.resources.getString(R.string.daily_notif_title))
            .setContentText(context.resources.getString(R.string.daily_notif_content))
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(999, notification)
    }

}