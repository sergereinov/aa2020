package com.github.sergereinov.aa2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), MovieDetailsFragment.FragmentClicks,
    MoviesListFragment.FragmentClicks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .apply {
                    add(R.id.fragments_container, MoviesListFragment())
                    commit()
                }
        }
    }

    private fun showMovieDetailsFragment(movieId: Int) {
        supportFragmentManager.beginTransaction()
            .apply {
                add(R.id.fragments_container, MovieDetailsFragment.newInstance(movieId))
                addToBackStack(null)
                commit()
            }
    }

    override fun backFromMovieDetails() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack() //enqueue async pop from backstack
        }
    }

    override fun filmCard(movieId: Int) {
        showMovieDetailsFragment(movieId)
    }

}