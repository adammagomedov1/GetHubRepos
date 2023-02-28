package com.magomedov.githubrepos

import com.magomedov.githubrepos.fragment.ProfileDetailsFragment
import com.magomedov.githubrepos.fragment.RepositoryListFragment
import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory

class AppNavigatorFactory : RegistryNavigationFactory() {
    init {
        registerFragment(NomeScreen::class.java , RepositoryListFragment::class.java)
        registerFragment(ProfileDetailsScreen::class.java, ProfileDetailsFragment::class.java)
    }
}