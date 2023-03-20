package com.magomedov.githubrepos.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.models.Repository
import kotlinx.coroutines.launch

class RepositoryListViewModels : ViewModel() {

    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<Throwable>()
    val repositoriesLiveData = MutableLiveData<List<Repository>>()

    fun loadRepositories() {
        viewModelScope.launch {
            try {
                progressBarLiveData.value = true

                val repositoryList: List<Repository> =
                    GitHubReposApplication.gitHubService.getRepositoryList()

                progressBarLiveData.value = false
                repositoriesLiveData.value = repositoryList

            } catch (e : Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e
            }
        }
    }
}