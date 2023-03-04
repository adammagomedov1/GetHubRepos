package com.magomedov.githubrepos.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.databinding.FragmentProfileDetailsBinding
import com.magomedov.githubrepos.models.Favorites
import com.magomedov.githubrepos.models.ProfileDetails
import com.magomedov.githubrepos.models.RepositoryDetails
import com.magomedov.githubrepos.network.FavoriteDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNREACHABLE_CODE")
class ProfileDetailsFragment : Fragment(R.layout.fragment_profile_details) {
    private var binding: FragmentProfileDetailsBinding? = null

    lateinit var getRepositoryProfile: Call<ProfileDetails>

    var repositoryProfile: ProfileDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileDetailsBinding.bind(view)

        binding!!.toolbarId.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
//                GitHubReposApplication.navigator.goBack()
            }
        })

        binding!!.toolbarId.setOnMenuItemClickListener(object : OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                if (R.id.mojombo_menu == item!!.itemId) {
                    // Добовление в базу даных
                    val authors = Favorites(
                        repositoryProfile!!.login,
                        repositoryProfile!!.avatar,
                        repositoryProfile!!.nameProfile
                    )
                    val authorsDao: FavoriteDao = GitHubReposApplication.appDatabase.favoritesDao()
                    authorsDao.insertAuthors(authors)

                    val addedProfile: Snackbar = Snackbar.make(
                        view, R.string.added_to_favorites, Snackbar.LENGTH_LONG
                    )
                    addedProfile.show()
                }
                return true
            }
        })

        val repositoryDetailsSerializable = requireArguments().getSerializable(ARGUMENT_PROFILE)
        val repositoryDetails : RepositoryDetails? = repositoryDetailsSerializable as? RepositoryDetails
        getRepositoryProfile =
            GitHubReposApplication.gitHubService.getRepositoryProfile(repositoryDetails!!.picture.login)


        getRepositoryProfile.enqueue(object : Callback<ProfileDetails> {

            override fun onResponse(
                call: Call<ProfileDetails>, response: Response<ProfileDetails>
            ) {

                repositoryProfile = response.body()
                if (repositoryProfile != null) {

                    binding!!.toolbarId.setTitle(repositoryProfile!!.nameProfile)

                    Glide.with(this@ProfileDetailsFragment).load(repositoryProfile!!.avatar)
                        .into(binding!!.avatar)

                    binding!!.profileDescription.setText(repositoryProfile!!.description)

                    binding!!.profileName.setText(repositoryProfile!!.nameProfile)

                    binding!!.login.setText(repositoryProfile!!.login)

                    binding!!.location.setText(repositoryProfile!!.locationProfile)

                    binding!!.gmailId.setText(repositoryProfile!!.emailProfile)

                    binding!!.numberOfSubscribers.setText(repositoryProfile!!.followersProfile)

                    binding!!.numberOfSubscriptions.setText(repositoryProfile!!.followingProfile)

                    binding!!.repositories.setText(repositoryProfile!!.publicReposProfile)

                }

            }

            override fun onFailure(call: Call<ProfileDetails>, t: Throwable) {

                val error: Snackbar = Snackbar.make(
                    requireView(), t.message!!, Snackbar.LENGTH_LONG
                )
                error.show()
            }
        })
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