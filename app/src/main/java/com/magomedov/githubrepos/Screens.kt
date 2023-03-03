package com.magomedov.githubrepos

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.magomedov.githubrepos.fragment.*
import com.magomedov.githubrepos.models.Repository
import com.magomedov.githubrepos.models.RepositoryDetails

object Screens {
    fun home() = FragmentScreen { RepositoryListFragment() }
    fun profile(repositoryDetails: RepositoryDetails) = FragmentScreen { ProfileDetailsFragment() }
    fun repositoryDetails(repository: Repository) = FragmentScreen { RepositoryDetailsFragment() }
    fun featuredAuthors() = FragmentScreen { FavoritesAuthorsFragment() }
    fun aboutApp() = FragmentScreen { AboutAppFragment() }
    fun settings() = FragmentScreen { SettingsFragment() }
}
