package com.github.sergereinov.aa2020.domain

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import androidx.core.net.toUri
import com.github.sergereinov.aa2020.MainActivity
import com.github.sergereinov.aa2020.R
import com.github.sergereinov.aa2020.database.MovieEntity

interface Notifications {
    fun showNotification(movie: MovieEntity)
    fun dismissNotification(movieId: Long)
}

class AndroidNotifications(private val context: Context) : Notifications {

    companion object {
        private const val CHANNEL_NEW_MOVIE = "new_movie"
        private const val REQUEST_CONTENT = 1
        private const val NEW_MOVIE_TAG = "new movie tag"
    }

    private val notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    init {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_NEW_MOVIE) == null) {
            notificationManagerCompat.createNotificationChannel(
                NotificationChannelCompat.Builder(CHANNEL_NEW_MOVIE, IMPORTANCE_HIGH)
                    .setName(context.getString(R.string.channel_new_movie))
                    .setDescription(context.getString(R.string.channel_new_movie_description))
                    .build()
            )
        }
    }

    override fun showNotification(movie: MovieEntity) {
        val contentUri = "com.github.sergereinov.aa2020://aa2020.local/movie/${movie.id}".toUri()
        val builder = NotificationCompat.Builder(context, CHANNEL_NEW_MOVIE)
            .setContentTitle(context.getString(R.string.there_is_a_new_movie))
            .setContentText(movie.title)
            .setSmallIcon(R.drawable.ic_star_outline)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(contentUri),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )

        notificationManagerCompat.notify(NEW_MOVIE_TAG, movie.id.toInt(), builder.build())
    }

    override fun dismissNotification(movieId: Long) {
        notificationManagerCompat.cancel(NEW_MOVIE_TAG, movieId.toInt())
    }
}
