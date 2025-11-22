package com.magomedov.githubrepos.fragment

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.magomedov.githubrepos.AppTheme
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.databinding.FragmentProfileDetailsBinding
import com.magomedov.githubrepos.models.RepositoryDetails
import com.magomedov.githubrepos.viewmodels.ProfileDetailsViewModels

class ProfileDetailsFragment : Fragment(R.layout.fragment_profile_details) {
    private var binding: FragmentProfileDetailsBinding? = null

    private val viewModel: ProfileDetailsViewModels by lazy {
        ViewModelProvider(this).get(ProfileDetailsViewModels::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileDetailsBinding.bind(view)

        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val profile by viewModel.profileDetailsLiveData.observeAsState()

                AppTheme {
                    ProfileDetailsContent(
                        profile = profile ?: return@AppTheme,
                        onFavoritesClick = {
                            viewModel.onMenuFavoritesClick()
                            val addedProfile: Snackbar = Snackbar.make(
                                view, R.string.added_to_favorites, Snackbar.LENGTH_LONG
                            )
                            addedProfile.show()
                        },
                        onBackClick = { GitHubReposApplication.router.exit() }
                    )
                }
            }
        }

        viewModel.failureLiveData.observe(viewLifecycleOwner, object : Observer<String> {
            override fun onChanged(t: String) {
                val error: Snackbar = Snackbar.make(
                    requireView(), t, Snackbar.LENGTH_LONG
                )
                error.show()
            }
        })

        val repositoryDetailsSerializable = requireArguments().getSerializable(ARGUMENT_PROFILE)
        val repositoryDetails: RepositoryDetails =
            repositoryDetailsSerializable as RepositoryDetails

        viewModel.loadProfileDetails(repositoryDetails.picture.login)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val ARGUMENT_PROFILE = "profile"

        fun createFragment(repositoryDetails: RepositoryDetails): Fragment {
            val fragment = ProfileDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENT_PROFILE, repositoryDetails)
            fragment.arguments = bundle

            return fragment
        }
    }
}