package com.github.sergereinov.aa2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), FragmentMoviesDetails.FragmentClicks, FragmentMoviesList.FragmentClicks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .apply {
                    add(R.id.fragments_container, FragmentMoviesList())
                    commit()
                }
        }
    }

    private fun showMovieDetailsFragment() {
        supportFragmentManager.beginTransaction()
            .apply {
                add(R.id.fragments_container, FragmentMoviesDetails())
                addToBackStack(null)
                commit()
            }
    }

    override fun backFromMovieDetails() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack() //enqueue async pop from backstack
        }
    }

    override fun filmCard() {
        showMovieDetailsFragment()
    }

}