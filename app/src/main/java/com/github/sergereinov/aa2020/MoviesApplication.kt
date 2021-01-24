package com.github.sergereinov.aa2020

import android.app.Application
import com.github.sergereinov.aa2020.database.MovieDatabase
import com.github.sergereinov.aa2020.network.INetworkInteractor
import com.github.sergereinov.aa2020.network.NetworkModule

class MoviesApplication : Application() {

    lateinit var networkModule: INetworkInteractor
        private set

    lateinit var database: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        networkModule = NetworkModule(applicationContext)
        database = MovieDatabase.create(applicationContext)
    }
}