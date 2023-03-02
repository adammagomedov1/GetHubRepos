package com.magomedov.githubrepos

import com.magomedov.githubrepos.models.Repository
import com.magomedov.githubrepos.models.RepositoryDetails
import me.aartikov.alligator.Screen

class NomeScreen : Screen
class ProfileDetailsScreen(val repositoryDetails: RepositoryDetails) : Screen, java.io.Serializable
class RepositoryDetailsScreen (val repository: Repository) : Screen , java.io.Serializable
class FeaturedAuthorsScreen : Screen
class AboutAppScreen : Screen
class SettingsScreen : Screen
