package com.magomedov.githubrepos.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.databinding.FragmentProfileDetailsBinding
import com.magomedov.githubrepos.models.ProfileDetails
import com.magomedov.githubrepos.models.RepositoryDetails
import com.magomedov.githubrepos.viewmodels.ProfileDetailsViewModels

@Suppress("UNREACHABLE_CODE")
class ProfileDetailsFragment : Fragment(R.layout.fragment_profile_details) {
    private var binding: FragmentProfileDetailsBinding? = null

    val viewModel: ProfileDetailsViewModels by lazy {
        ViewModelProvider(this).get(ProfileDetailsViewModels::class.java)
    }

    var repositoryProfile: ProfileDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileDetailsBinding.bind(view)

        binding!!.toolbarId.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                GitHubReposApplication.router.exit()
            }
        })

        binding!!.toolbarId.setOnMenuItemClickListener(object : OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                if (R.id.mojombo_menu == item!!.itemId) {
                    viewModel.onMenuFavoritesClick()

                    val addedProfile: Snackbar = Snackbar.make(
                        view, R.string.added_to_favorites, Snackbar.LENGTH_LONG
                    )
                    addedProfile.show()
                }
                return true
            }
        })

        viewModel.failureLiveData.observe(viewLifecycleOwner, object : Observer<String> {
            override fun onChanged(t: String) {
                val error: Snackbar = Snackbar.make(
                    requireView(), t, Snackbar.LENGTH_LONG
                )
                error.show()
            }
        })

        val repositoryDetailsSerializable = requireArguments().getSerializable(ARGUMENT_PROFILE)
        val repositoryDetails: RepositoryDetails? =
            repositoryDetailsSerializable as? RepositoryDetails

        viewModel.profileDetailsLiveData.observe(viewLifecycleOwner, object : Observer<ProfileDetails>{
            override fun onChanged(t: ProfileDetails) {
                repositoryProfile = t

                binding!!.toolbarId.setTitle(t.nameProfile)

                Glide.with(this@ProfileDetailsFragment).load(t.avatar)
                    .into(binding!!.avatar)

                binding!!.profileDescription.setText(t.description)

                binding!!.profileName.setText(t.nameProfile)

                binding!!.login.setText(t.login)

                binding!!.location.setText(t.locationProfile)

                binding!!.gmailId.setText(t.emailProfile)

                binding!!.numberOfSubscribers.setText(t.followersProfile)

                binding!!.numberOfSubscriptions.setText(t.followingProfile)

                binding!!.repositories.setText(t.publicReposProfile)
            }
        })
        viewModel.loadProfileDetails(repositoryDetails!!.picture.login)
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