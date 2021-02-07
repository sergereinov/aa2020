package com.github.sergereinov.aa2020.worker

import androidx.work.*
import java.util.concurrent.TimeUnit

object WorkRequests {
    val refreshMoviesNow
        get() =
            OneTimeWorkRequest.Builder(RefreshMoviesWorker::class.java).build()

    val refreshMoviesPeriodic: PeriodicWorkRequest
        get() {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build()
            return PeriodicWorkRequest
                .Builder(RefreshMoviesWorker::class.java, 8, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()
        }
}
