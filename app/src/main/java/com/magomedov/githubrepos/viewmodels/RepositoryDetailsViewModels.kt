package com.magomedov.githubrepos.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.models.RepositoryDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDetailsViewModels : ViewModel() {

    lateinit var getRepositoryDetails: Call<RepositoryDetails>

    val repositoryDetailsLiveData = MutableLiveData<RepositoryDetails>()
    val failureLiveData = MutableLiveData<String>()

    fun loadRepositoryDetails(repositoryId: Int) {
        getRepositoryDetails = GitHubReposApplication.gitHubService.getRepositoryDetails(repositoryId)

        getRepositoryDetails.enqueue(object : Callback<RepositoryDetails> {

            override fun onResponse(
                call: Call<RepositoryDetails>,
                response: Response<RepositoryDetails>
            ) {
                val repositoryDetails: RepositoryDetails = response.body()!!
                repositoryDetailsLiveData.value = repositoryDetails
            }

            override fun onFailure(call: Call<RepositoryDetails>, t: Throwable) {
                failureLiveData.value = t.message!!
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        getRepositoryDetails.cancel()
    }
}