package com.magomedov.githubrepos.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.models.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryListViewModels : ViewModel() {
    var getRepositoryCall: Call<List<Repository>> =
        GitHubReposApplication.gitHubService.getRepositoryList()

    val progressBarData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<Throwable>()
    val repositoriesLiveData = MutableLiveData<List<Repository>>()


    fun loadRepositories() {
        progressBarData.value = true

        getRepositoryCall.enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                progressBarData.value = false
                if (response.isSuccessful) {
                    val repositoriesList: List<Repository>? = response.body()
                    repositoriesLiveData.value = repositoriesList
                }
            }

            val sharedPreferences = GitHubReposApplication.context.getSharedPreferences(
                "git_hub_preferences",
                Context.MODE_PRIVATE
            )

            val repositoriesEditor: String? =
                sharedPreferences.getString("repositories", null)

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                progressBarData.value = false
                errorLiveData.value = t
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        getRepositoryCall.cancel()
    }
}