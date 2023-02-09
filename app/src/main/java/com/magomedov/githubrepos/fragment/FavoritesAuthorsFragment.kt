package com.magomedov.githubrepos.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.adapters.FavoritesAuthorsAdapter
import com.magomedov.githubrepos.models.Favorites
import com.magomedov.githubrepos.network.FavoriteDao

class FavoritesAuthorsFragment : Fragment(R.layout.fragment_featired_authors) {

    val authorsDao: FavoriteDao = GitHubReposApplication.appDatabase.favoritesDao()

    private val authorsAdapter = FavoritesAuthorsAdapter(
        itemAuthorsListener = object : FavoritesAuthorsAdapter.AuthorsListener {

            override fun onDelete(authors: Favorites) {
                authorsDao.deleteAuthors(authors)
                updateAuthorsList()
            }
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })

        val authorsRecyclerView: RecyclerView = view.findViewById(R.id.featured_authors)
        authorsRecyclerView.setAdapter(authorsAdapter)

        val dividerAuthors = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        authorsRecyclerView.addItemDecoration(dividerAuthors)

        updateAuthorsList()

        val listOf = authorsAdapter.authors

        val nameEditText: EditText = view.findViewById(R.id.sorting)
        nameEditText.addTextChangedListener(object : TextWatcher {
            //Вызывается ДО изменения текста
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            //Вызывается ВО ВРЕМЯ изменения текста
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            //Вызывается ПОСЛЕ изменения текста (используется, в основном)
            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(s: Editable?) {
                // Берём исходный список авторов
                val filter: List<Favorites> =
                    listOf.filter { it.nameProfile.contains(s!!.toString(), ignoreCase = true) }
                authorsAdapter.authors = filter
                authorsAdapter.notifyDataSetChanged()

            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAuthorsList() {
        val authorsList: List<Favorites> = authorsDao.getAllAuthors()
        authorsAdapter.authors = authorsList
        authorsAdapter.notifyDataSetChanged()
    }
}