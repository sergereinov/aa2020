package com.github.sergereinov.aa2020

import android.app.Application
import com.github.sergereinov.aa2020.domain.AppContainer

class MoviesApplication : Application() {
    val appContainer = AppContainer()
}