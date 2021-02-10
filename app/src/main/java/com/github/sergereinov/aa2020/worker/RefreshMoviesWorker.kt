package com.github.sergereinov.aa2020.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.sergereinov.aa2020.domain.IMoviesInteractor
import java.lang.Exception

class RefreshMoviesWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val moviesInteractor: IMoviesInteractor
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "doWork starts")
            moviesInteractor.refreshMovies()
            Log.d(TAG, "doWork done")
            Result.success()
        } catch (e: Exception) {
            Log.d(
                TAG, e.localizedMessage ?: e.message ?: e.toString()
            )
            Result.failure()
        }
    }

    companion object {
        private val TAG = RefreshMoviesWorker::class.java.name
    }
}
