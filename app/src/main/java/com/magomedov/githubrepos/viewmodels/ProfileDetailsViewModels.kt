package com.magomedov.githubrepos.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.models.Favorites
import com.magomedov.githubrepos.models.ProfileDetails
import com.magomedov.githubrepos.database.FavoriteDao
import kotlinx.coroutines.launch

class ProfileDetailsViewModels : ViewModel() {
    val profileDetailsLiveData = MutableLiveData<ProfileDetails>()
    val failureLiveData = MutableLiveData<String>()

    fun loadProfileDetails(login: String) {
        viewModelScope.launch {
            try {
                val repositoryProfile: ProfileDetails =
                    GitHubReposApplication.gitHubService.getRepositoryProfile(login)

                profileDetailsLiveData.value = repositoryProfile
            } catch (e: Exception){
                failureLiveData.value = e.message
            }
        }
    }

    fun onMenuFavoritesClick() {
        viewModelScope.launch {
            val repositoryProfile = profileDetailsLiveData.value!!
            // Добовление в базу даных
            val authors = Favorites(
                repositoryProfile.login,
                repositoryProfile.avatar,
                repositoryProfile.nameProfile
            )
            val authorsDao: FavoriteDao = GitHubReposApplication.appDatabase.favoritesDao()
            authorsDao.insertAuthors(authors)
        }

    }
}