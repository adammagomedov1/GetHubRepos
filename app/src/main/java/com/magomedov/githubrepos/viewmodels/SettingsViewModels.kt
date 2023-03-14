package com.magomedov.githubrepos.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magomedov.githubrepos.GitHubReposApplication

class SettingsViewModels : ViewModel() {

    val repositoryLiveData = MutableLiveData<String>()

    fun loadSettings() {
        val sharedPreferences = GitHubReposApplication.context.getSharedPreferences(
            "git_hub_preferences",
            Context.MODE_PRIVATE
        )
        val repositoriesEditor: String? = sharedPreferences.getString("repositories", null)
        repositoryLiveData.value = repositoriesEditor
    }

    fun onSaveSettings(
        repositoryList : String
    ) {
        val sharedPreferences = GitHubReposApplication.context.getSharedPreferences(
            "git_hub_preferences",
            Context.MODE_PRIVATE
        )

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("repositories", repositoryList)
        editor.apply()
    }
}