package com.magomedov.githubrepos

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.magomedov.githubrepos.fragment.RepositoryListFragment

object Screens {
    fun home() = FragmentScreen { RepositoryListFragment() }
}
