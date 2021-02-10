package com.github.sergereinov.aa2020

import android.app.Application
import androidx.work.*
import com.github.sergereinov.aa2020.database.MovieDatabase
import com.github.sergereinov.aa2020.domain.IMoviesInteractor
import com.github.sergereinov.aa2020.domain.InteractorsProvider
import com.github.sergereinov.aa2020.domain.MoviesInteractor
import com.github.sergereinov.aa2020.network.INetworkInteractor
import com.github.sergereinov.aa2020.network.NetworkModule
import com.github.sergereinov.aa2020.worker.MoviesWorkerFactory
import com.github.sergereinov.aa2020.worker.WorkRequests

class MoviesApplication : Application(), Configuration.Provider, InteractorsProvider {

    lateinit var networkModule: INetworkInteractor
        private set

    lateinit var database: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        networkModule = NetworkModule(applicationContext)
        database = MovieDatabase.create(applicationContext)

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
        return MoviesInteractor(networkModule, database)
    }
}
