package com.magomedov.githubrepos.fragment

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.Screens
import com.magomedov.githubrepos.databinding.FragmentRepositoryDetailsBinding
import com.magomedov.githubrepos.models.Repository
import com.magomedov.githubrepos.models.RepositoryDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class RepositoryDetailsFragment : Fragment(R.layout.fragment_repository_details) {
    private var binding: FragmentRepositoryDetailsBinding? = null

    lateinit var getRepositoryDetails: Call<RepositoryDetails>

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
                requireActivity().supportFragmentManager.popBackStack()
            }
        })

        val repositoryId: Serializable? = requireArguments().getSerializable(ARGUMENT_DETAILS)
        val repository = repositoryId as? Repository
        getRepositoryDetails =
            GitHubReposApplication.gitHubService.getRepositoryDetails(repository!!.id)

        getRepositoryDetails.enqueue(object : Callback<RepositoryDetails> {

            override fun onResponse(
                call: Call<RepositoryDetails>,
                response: Response<RepositoryDetails>
            ) {
                repositoryDetails = response.body()
                if (repositoryDetails != null) {

                    val toolbar1: Toolbar = view.findViewById(R.id.toolbar)
                    toolbar1.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
                        override fun onMenuItemClick(item: MenuItem?): Boolean {
                            if (item!!.itemId == R.id.will_share) {

                                val linkId: String = binding!!.linkId.text.toString()

                                startIntent(linkId)
                            }
                            return true
                        }
                    })

                    Glide.with(this@RepositoryDetailsFragment)
                        .load(repositoryDetails!!.picture.avatar)
                        .into(binding!!.avatar)

                    binding!!.toolbar.setTitle(repositoryDetails!!.nameDetails)

                    binding!!.name.setText(repositoryDetails!!.nameDetails)

                    binding!!.login.setText(repositoryDetails!!.picture.login)

                    binding!!.description.setText(repositoryDetails!!.descriptionDetails)

                    binding!!.linkId.setText(repositoryDetails!!.id)

                    binding!!.stargazersOunt.setText(repositoryDetails!!.gradeDetails)

                    binding!!.numberOfRedirects.setText(repositoryDetails!!.repostDetails)

                    binding!!.numberOfMistakes.setText(repositoryDetails!!.mistakesDetails)

                }
            }

            override fun onFailure(call: Call<RepositoryDetails>, t: Throwable) {

                val error: Snackbar = Snackbar.make(
                    requireView(),
                    t.message!!,
                    Snackbar.LENGTH_LONG
                )
                error.show()
            }
        })
    }

    private fun startIntent(misage: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, misage)

        startActivity(shareIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val ARGUMENT_DETAILS = "deletes"

        fun createArgument(repository: Repository): Fragment {
            val fragment = RepositoryDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENT_DETAILS, repository)
            fragment.arguments = bundle
            return fragment
        }
    }
}