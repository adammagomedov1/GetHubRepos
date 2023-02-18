package com.magomedov.githubrepos.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.models.Favorites
import com.magomedov.githubrepos.models.ProfileDetails
import com.magomedov.githubrepos.network.FavoriteDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNREACHABLE_CODE")
class ProfileDetailsFragment : Fragment(R.layout.fragment_profile_details) {

    lateinit var getRepositoryProfile: Call<ProfileDetails>

    var repositoryProfile: ProfileDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar_id)
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })

        toolbar.setOnMenuItemClickListener(object : OnMenuItemClickListener {
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
                        view,
                        R.string.added_to_favorites,
                        Snackbar.LENGTH_LONG
                    )
                    addedProfile.show()
                }
                return true
            }
        })

        val loginParam: String? = requireArguments().getString("login", null)
        getRepositoryProfile = GitHubReposApplication.gitHubService.getRepositoryProfile(loginParam)

        getRepositoryProfile.enqueue(object : Callback<ProfileDetails> {

            override fun onResponse(
                call: Call<ProfileDetails>,
                response: Response<ProfileDetails>
            ) {

                repositoryProfile = response.body()
                if (repositoryProfile != null) {

                    val tolBarView: Toolbar = view.findViewById(R.id.toolbar_id)
                    tolBarView.setTitle(repositoryProfile!!.nameProfile)

                    val avatar: ImageView = view.findViewById(R.id.avatar)
                    Glide.with(this@ProfileDetailsFragment)
                        .load(repositoryProfile!!.avatar)
                        .into(avatar)

                    val description : TextView = view.findViewById(R.id.profile_description)
                    description.setText(repositoryProfile!!.description)

                    val name: TextView = view.findViewById(R.id.profile_name)
                    name.setText(repositoryProfile!!.nameProfile)

                    val mojombo: TextView = view.findViewById(R.id.login)
                    mojombo.setText(repositoryProfile!!.login)

                    val location: TextView = view.findViewById(R.id.location)
                    location.setText(repositoryProfile!!.locationProfile)

                    val gmail: TextView = view.findViewById(R.id.gmail_id)
                    gmail.setText(repositoryProfile!!.emailProfile)

                    val number: TextView = view.findViewById(R.id.number_of_subscribers)
                    number.setText(repositoryProfile!!.followersProfile)

                    val numberOfSubscriptions: TextView =
                        view.findViewById(R.id.number_of_subscriptions)
                    numberOfSubscriptions.setText(repositoryProfile!!.followingProfile)

                    val repositories: TextView = view.findViewById(R.id.repositories)
                    repositories.setText(repositoryProfile!!.publicReposProfile)

                }

            }

            override fun onFailure(call: Call<ProfileDetails>, t: Throwable) {

                val error: Snackbar = Snackbar.make(
                    requireView(),
                    t.message!!,
                    Snackbar.LENGTH_LONG
                )
                error.show()
            }
        })
    }
}