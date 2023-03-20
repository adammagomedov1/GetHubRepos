package com.magomedov.githubrepos.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.database.FavoriteDao
import com.magomedov.githubrepos.models.Favorites
import kotlinx.coroutines.launch

class FavoritesAuthorsViewModels : ViewModel() {

    val authorsDao: FavoriteDao = GitHubReposApplication.appDatabase.favoritesDao()

    val favoritesLiveData = MutableLiveData<List<Favorites>>()

    fun updateAuthorsList() {
        viewModelScope.launch {
            val authorsList: List<Favorites> = authorsDao.getAllAuthors()
            favoritesLiveData.value = authorsList
        }
    }

    fun onDelete(authors: Favorites) {
        viewModelScope.launch {
            authorsDao.deleteAuthors(authors)
            updateAuthorsList()
        }
    }

    fun afterTextChanged(text: String) {
        // Берём исходный список авторов
        viewModelScope.launch {
            val baseList: List<Favorites> = authorsDao.getAllAuthors()

            val filter: List<Favorites> =
                baseList.filter { it.nameProfile.contains(text, ignoreCase = true) }
            favoritesLiveData.value = filter
        }
    }

}