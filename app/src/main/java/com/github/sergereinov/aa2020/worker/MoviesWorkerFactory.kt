package com.github.sergereinov.aa2020.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.github.sergereinov.aa2020.domain.IMoviesInteractor

class MoviesWorkerFactory(private val moviesInteractor: IMoviesInteractor) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            RefreshMoviesWorker::class.java.name -> RefreshMoviesWorker(
                appContext,
                workerParameters,
                moviesInteractor
            )
            else -> null //or else switch to default WorkerFactory
        }
    }
}
