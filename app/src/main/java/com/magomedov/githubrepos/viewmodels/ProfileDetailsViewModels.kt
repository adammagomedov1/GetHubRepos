package com.magomedov.githubrepos.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.models.Favorites
import com.magomedov.githubrepos.models.ProfileDetails
import com.magomedov.githubrepos.network.FavoriteDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileDetailsViewModels: ViewModel() {
    lateinit var getRepositoryProfile: Call<ProfileDetails>
    val profileDetailsLiveData = MutableLiveData<ProfileDetails>()
    val failureLiveData = MutableLiveData<String>()

    fun loadProfileDetails (login: String) {
        getRepositoryProfile = GitHubReposApplication.gitHubService.getRepositoryProfile(login)
        getRepositoryProfile.enqueue(object : Callback<ProfileDetails> {
            override fun onResponse(
                call: Call<ProfileDetails>,
                response: Response<ProfileDetails>
            ) {
                val profileInfo: ProfileDetails? = response.body()
                profileDetailsLiveData.value = profileInfo

            }

            override fun onFailure(call: Call<ProfileDetails>, t: Throwable) {
                failureLiveData.value = t.message
            }
        })
    }

    fun onMenuFavoritesClick(){
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

    override fun onCleared() {
        super.onCleared()
        getRepositoryProfile.cancel()
    }

}