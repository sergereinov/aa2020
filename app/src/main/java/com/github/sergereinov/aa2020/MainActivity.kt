package com.github.sergereinov.aa2020

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.github.sergereinov.aa2020.domain.InteractorsProvider

class MainActivity : AppCompatActivity(), MovieDetailsFragment.FragmentClicks,
    MoviesListFragment.FragmentClicks {

    companion object {
        private const val DETAILS_FRAGMENT_TAG = "movie details fragment tag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .apply {
                    add(R.id.fragments_container, MoviesListFragment())
                    commit()
                }
            intent?.let(::handleIntent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let(::handleIntent)
    }

    /*
        Run in terminal to test/debug
        adb shell am start -W -a android.intent.action.VIEW -d 'com.github.sergereinov.aa2020://aa2020.local/movie/539885'
     */
    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_VIEW) {
            val id = intent.data?.lastPathSegment?.toIntOrNull()
            if (id != null) {
                (application as InteractorsProvider).getNotificationsInteractor()
                    .dismissNotification(id.toLong())
                showMovieDetailsFragment(id)
            }
        }
    }

    private fun showMovieDetailsFragment(movieId: Int, itemView: View? = null) {
        supportFragmentManager.popBackStack(
            DETAILS_FRAGMENT_TAG,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        supportFragmentManager.beginTransaction()
            .apply {
                itemView?.let { sourceView ->
                    setReorderingAllowed(true)
                    addSharedElement(sourceView, getString(R.string.details_fragment_transition_name))
                }
                replace(R.id.fragments_container, MovieDetailsFragment.newInstance(movieId))
                addToBackStack(DETAILS_FRAGMENT_TAG)
                commit()
            }
    }

    override fun backFromMovieDetails() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack() //enqueue async pop from backstack
        }
    }

    override fun filmCard(movieId: Int, itemView: View) {
        showMovieDetailsFragment(movieId, itemView)
    }

}