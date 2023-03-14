package com.magomedov.githubrepos.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.Screens
import com.magomedov.githubrepos.databinding.FragmentRepositoryDetailsBinding
import com.magomedov.githubrepos.models.Repository
import com.magomedov.githubrepos.models.RepositoryDetails
import com.magomedov.githubrepos.viewmodels.RepositoryDetailsViewModels
import java.io.Serializable

class RepositoryDetailsFragment : Fragment(R.layout.fragment_repository_details) {
    private var binding: FragmentRepositoryDetailsBinding? = null

    val viewModel: RepositoryDetailsViewModels by lazy {
        ViewModelProvider(this).get(RepositoryDetailsViewModels::class.java)
    }

    var repositoryDetails: RepositoryDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryDetailsBinding.bind(view)

        binding!!.linearlayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                GitHubReposApplication.router.navigateTo(Screens.profile(repositoryDetails!!))
            }
        })

        binding!!.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                GitHubReposApplication.router.exit()
            }
        })

        binding!!.toolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                if (item!!.itemId == R.id.will_share) {
                    startIntent()
                }
                return true
            }
        })

        val repositoryId: Serializable? = requireArguments().getSerializable(ARGUMENT_DETAILS)
        val repository = repositoryId as? Repository

        viewModel.repositoryDetailsLiveData.observe(
            viewLifecycleOwner,
            object : Observer<RepositoryDetails> {
                override fun onChanged(t: RepositoryDetails) {
                    repositoryDetails = t

                    Glide.with(this@RepositoryDetailsFragment)
                        .load(t.picture.avatar)
                        .into(binding!!.avatar)

                    binding!!.toolbar.setTitle(t.nameDetails)

                    binding!!.name.setText(t.nameDetails)

                    binding!!.login.setText(t.picture.login)

                    binding!!.description.setText(t.descriptionDetails)

                    binding!!.linkId.setText(t.htmlUrl)

                    binding!!.stargazersOunt.setText(t.gradeDetails)

                    binding!!.numberOfRedirects.setText(t.repostDetails)

                    binding!!.numberOfMistakes.setText(t.mistakesDetails)
                }
            })

        viewModel.failureLiveData.observe(viewLifecycleOwner, object : Observer<String> {
            override fun onChanged(t: String) {
                val error: Snackbar = Snackbar.make(
                    requireView(),
                    t,
                    Snackbar.LENGTH_LONG
                )
                error.show()
            }
        })
        viewModel.loadRepositoryDetails(repository!!.id)
    }

    private fun startIntent() {
        GitHubReposApplication.router.navigateTo(Screens.share(repositoryDetails!!.htmlUrl))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val ARGUMENT_DETAILS = "deletes"

        fun createFragment(repository: Repository): Fragment {
            val fragment = RepositoryDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENT_DETAILS, repository)
            fragment.arguments = bundle
            return fragment
        }
    }
}