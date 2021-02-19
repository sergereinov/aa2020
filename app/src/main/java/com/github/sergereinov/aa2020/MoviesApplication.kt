package com.github.sergereinov.aa2020

import android.app.Application
import androidx.work.*
import com.github.sergereinov.aa2020.database.MovieDatabase
import com.github.sergereinov.aa2020.domain.*
import com.github.sergereinov.aa2020.network.INetworkInteractor
import com.github.sergereinov.aa2020.network.NetworkModule
import com.github.sergereinov.aa2020.worker.MoviesWorkerFactory
import com.github.sergereinov.aa2020.worker.WorkRequests

class MoviesApplication : Application(), Configuration.Provider, InteractorsProvider {

    private lateinit var networkModule: INetworkInteractor
    private lateinit var database: MovieDatabase
    private lateinit var notifications: Notifications

    override fun onCreate() {
        super.onCreate()

        networkModule = NetworkModule(applicationContext)
        database = MovieDatabase.create(applicationContext)
        notifications = AndroidNotifications(applicationContext)

        val workManager = WorkManager.getInstance(applicationContext)
        //workManager.enqueue(WorkRequests.refreshMoviesNow) //uncomment to test/debug worker
        workManager.enqueueUniquePeriodicWork(
            "refreshMoviesPeriodic",
            ExistingPeriodicWorkPolicy.KEEP,
            WorkRequests.refreshMoviesPeriodic
        )
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val moviesWorkerFactory = DelegatingWorkerFactory()
        moviesWorkerFactory.addFactory(MoviesWorkerFactory(createMoviesInteractor()))

        return Configuration.Builder()
            .setWorkerFactory(moviesWorkerFactory)
            .build()
    }

    override fun createMoviesInteractor(): IMoviesInteractor {
        return MoviesInteractor(networkModule, database, notifications)
    }

    override fun getNotificationsInteractor(): Notifications {
        return notifications
    }
}
