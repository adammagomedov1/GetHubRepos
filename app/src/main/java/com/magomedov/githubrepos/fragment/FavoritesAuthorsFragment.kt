package com.magomedov.githubrepos.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.adapters.FavoritesAuthorsAdapter
import com.magomedov.githubrepos.databinding.FragmentFeatiredAuthorsBinding
import com.magomedov.githubrepos.models.Favorites
import com.magomedov.githubrepos.viewmodels.FavoritesAuthorsViewModels

class FavoritesAuthorsFragment : Fragment(R.layout.fragment_featired_authors) {
    private var binding: FragmentFeatiredAuthorsBinding? = null

    val viewModel: FavoritesAuthorsViewModels by lazy {
        ViewModelProvider(this).get(FavoritesAuthorsViewModels::class.java)
    }

    private val authorsAdapter = FavoritesAuthorsAdapter(
        itemAuthorsListener = object : FavoritesAuthorsAdapter.AuthorsListener {

            override fun onDelete(authors: Favorites) {
                viewModel.onDelete(authors)
            }
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeatiredAuthorsBinding.bind(view)

        binding!!.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                GitHubReposApplication.router.exit()
            }
        })

        binding!!.featuredAuthors.setAdapter(authorsAdapter)

        val dividerAuthors = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding!!.featuredAuthors.addItemDecoration(dividerAuthors)


        val listOf = authorsAdapter.authors

        binding!!.sorting.addTextChangedListener(object : TextWatcher {
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
                viewModel.afterTextChanged(s!!.toString())
            }
        })
        viewModel.favoritesLiveData.observe(viewLifecycleOwner, object : Observer<List<Favorites>> {
            override fun onChanged(t: List<Favorites>) {
                authorsAdapter.authors = t
                authorsAdapter.notifyDataSetChanged()
            }
        })
        viewModel.updateAuthorsList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}