package com.magomedov.githubrepos.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.Screens
import com.magomedov.githubrepos.adapters.RepositoryAdapter
import com.magomedov.githubrepos.databinding.FragmentRepositoriesListBinding
import com.magomedov.githubrepos.models.Repository
import com.magomedov.githubrepos.viewmodels.RepositoryListViewModels

// Экран отоброжаюший репозиториев
class RepositoryListFragment : Fragment(R.layout.fragment_repositories_list) {
    private var binding: FragmentRepositoriesListBinding? = null

    val viewModel: RepositoryListViewModels by lazy {
        ViewModelProvider(this).get(RepositoryListViewModels::class.java)
    }

    val repositoryAdapter =
        RepositoryAdapter(repositoryListener = object : RepositoryAdapter.RepositoryListener {
            override fun onItemClick(repository: Repository) {
                GitHubReposApplication.router.navigateTo(Screens.repositoryDetails(repository))
            }
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoriesListBinding.bind(view)

        binding!!.toolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                if (item?.itemId == R.id.featured_authors_menu) {
                    GitHubReposApplication.router.navigateTo(Screens.featuredAuthors())
                }
                if (item?.itemId == R.id.about_application_menu) {
                    GitHubReposApplication.router.navigateTo(Screens.aboutApp())
                }
                if (item?.itemId == R.id.settings_menu) {
                    GitHubReposApplication.router.navigateTo(Screens.settings())
                }
                return true
            }
        })

        val spinner: Spinner = view.findViewById(R.id.spinner)
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    val sort: List<Repository> = repositoryAdapter.repositoryList.sortedBy { it.id }
                    repositoryAdapter.repositoryList = sort
                }
                if (position == 1) {
                    val sorted =
                        repositoryAdapter.repositoryList.sortedBy { repository: Repository -> repository.name }
                    repositoryAdapter.repositoryList = sorted

                }
                repositoryAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })

        binding!!.listRecyclerview.adapter = repositoryAdapter

        val decider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding!!.listRecyclerview.addItemDecoration(decider)

        viewModel.repositoriesLiveData.observe(viewLifecycleOwner,
            object : Observer<List<Repository>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onChanged(t: List<Repository>?) {
                    if (t != null) {
                        repositoryAdapter.repositoryList = t.toMutableList()
                        repositoryAdapter.notifyDataSetChanged()
                    }
                }
            })

        viewModel.progressBarLiveData.observe(viewLifecycleOwner, object : Observer<Boolean> {
            override fun onChanged(t: Boolean?) {
                if (t == true) {
                    binding!!.linkProgressbar.visibility = View.VISIBLE
                } else {
                    binding!!.linkProgressbar.visibility = View.GONE
                }
            }
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, object : Observer<Throwable> {
            override fun onChanged(t: Throwable?) {
                val error: Snackbar = Snackbar.make(
                    requireView(),
                    t!!.message!!,
                    Snackbar.LENGTH_LONG
                )
                error.show()
            }
        })
        viewModel.loadRepositories()
    }

//    private fun loadRepositories() {
//        binding!!.linkProgressbar.visibility = View.VISIBLE
//
//        val getRepository: Call<List<Repository>> =
//            GitHubReposApplication.gitHubService.getRepositoryList()
//        getRepository.enqueue(object : Callback<List<Repository>> {
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onResponse(
//                call: Call<List<Repository>>,
//                response: Response<List<Repository>>
//            ) {
//                binding!!.linkProgressbar.visibility = View.GONE
//
//                val repositoryList: List<Repository> = response.body()!!
//
//                val sharedPreferences = requireContext().getSharedPreferences(
//                    "git_hub_preferences",
//                    Context.MODE_PRIVATE
//                )
//                val repositoriesEditor: String? =
//                    sharedPreferences.getString("repositories", null)
//
//                var repositoriesInt = 0
//
//                repositoriesInt = if (!repositoriesEditor.isNullOrEmpty()) {
//                    repositoriesEditor.toInt()
//                } else {
//                    repositoryList.size
//                }
//
//                val takeList: List<Repository> = repositoryList.take(repositoriesInt)
//                repositoryAdapter.repositoryList = takeList.toMutableList()
//                repositoryAdapter.notifyDataSetChanged()
//
//            }
//
//            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
//                binding!!.linkProgressbar.visibility = View.GONE
//
//                val error: Snackbar = Snackbar.make(
//                    requireView(),
//                    t.message!!,
//                    Snackbar.LENGTH_LONG
//                )
//                error.show()
//            }
//        })
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }
//        loadRepositories()

}