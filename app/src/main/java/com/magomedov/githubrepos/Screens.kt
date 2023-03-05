package com.magomedov.githubrepos

import android.content.Intent
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.magomedov.githubrepos.fragment.*
import com.magomedov.githubrepos.models.Repository
import com.magomedov.githubrepos.models.RepositoryDetails

object Screens {
    fun home() = FragmentScreen { RepositoryListFragment() }
    fun repositoryDetails(repository: Repository) =
        FragmentScreen { RepositoryDetailsFragment.createFragment(repository = repository) }

    fun profile(repositoryDetails: RepositoryDetails) =
        FragmentScreen { ProfileDetailsFragment.createFragment(repositoryDetails = repositoryDetails) }

    fun featuredAuthors() = FragmentScreen { FavoritesAuthorsFragment() }
    fun aboutApp() = FragmentScreen { AboutAppFragment() }
    fun settings() = FragmentScreen { SettingsFragment() }

    //Внешние экраны
    fun share(link: String) = ActivityScreen {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        shareIntent
    }
}
