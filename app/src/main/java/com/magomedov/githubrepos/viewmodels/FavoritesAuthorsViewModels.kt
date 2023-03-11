package com.magomedov.githubrepos.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.models.Favorites
import com.magomedov.githubrepos.network.FavoriteDao

class FavoritesAuthorsViewModels : ViewModel() {

    val authorsDao: FavoriteDao = GitHubReposApplication.appDatabase.favoritesDao()

    val favoritesLiveData = MutableLiveData<List<Favorites>>()
    val baseList: List<Favorites> = authorsDao.getAllAuthors()

    fun updateAuthorsList() {
        val authorsList: List<Favorites> = authorsDao.getAllAuthors()
        favoritesLiveData.value = authorsList
    }

    fun onDelete(authors: Favorites) {
        authorsDao.deleteAuthors(authors)
        updateAuthorsList()
    }

    fun afterTextChanged(text: String) {
        // Берём исходный список авторов
        val filter: List<Favorites> =
            baseList.filter { it.nameProfile.contains(text, ignoreCase = true) }
        favoritesLiveData.value = filter
    }

}