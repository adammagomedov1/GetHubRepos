package com.magomedov.githubrepos

import com.magomedov.githubrepos.fragment.*
import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory

class AppNavigatorFactory : RegistryNavigationFactory() {
    init {
        registerFragment(NomeScreen::class.java , RepositoryListFragment::class.java)
        registerFragment(ProfileDetailsScreen::class.java, ProfileDetailsFragment::class.java)
        registerFragment(FeaturedAuthorsScreen::class.java, FavoritesAuthorsFragment::class.java)
        registerFragment(AboutAppScreen::class.java, AboutAppFragment::class.java)
        registerFragment(SettingsScreen::class.java, SettingsFragment::class.java)
        registerFragment(RepositoryDetailsScreen::class.java, RepositoryDetailsFragment::class.java)
    }
}