package com.magomedov.githubrepos.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.models.RepositoryDetails
import kotlinx.coroutines.launch

class RepositoryDetailsViewModels : ViewModel() {
    val repositoryDetailsLiveData = MutableLiveData<RepositoryDetails>()
    val failureLiveData = MutableLiveData<String>()

    fun loadRepositoryDetails(repositoryId: Int) {
        viewModelScope.launch {
            try {
                val repositoryDetails: RepositoryDetails =
                    GitHubReposApplication.gitHubService.getRepositoryDetails(repositoryId)

                repositoryDetailsLiveData.value = repositoryDetails
            } catch (e: Exception) {
                failureLiveData.value = e.message!!
            }
        }
    }
}