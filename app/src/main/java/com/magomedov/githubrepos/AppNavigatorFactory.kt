package com.magomedov.githubrepos

import com.magomedov.githubrepos.fragment.RepositoryListFragment
import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory

class AppNavigatorFactory : RegistryNavigationFactory() {
    init {
        registerFragment(NomeScreen::class.java , RepositoryListFragment::class.java)
    }
}