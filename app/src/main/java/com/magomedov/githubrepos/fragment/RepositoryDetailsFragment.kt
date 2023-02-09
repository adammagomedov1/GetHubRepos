package com.magomedov.githubrepos.fragment

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.models.RepositoryDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDetailsFragment : Fragment(R.layout.fragment_repository_details) {

    lateinit var getRepositoryDetails: Call<RepositoryDetails>

    var repositoryDetails: RepositoryDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayout: LinearLayout = view.findViewById(R.id.linearlayout)
        linearLayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val repositoryProfileFragment = ProfileDetailsFragment()
                val bundle = Bundle()
                bundle.putString("login", repositoryDetails!!.picture.login)
                repositoryProfileFragment.arguments = bundle

                val transaction: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_main_container, repositoryProfileFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })

        val repositoryId: Int = requireArguments().getInt("id", 0)
        getRepositoryDetails =
            GitHubReposApplication.gitHubService.getRepositoryDetails(repositoryId)

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

                                val link: TextView = view.findViewById(R.id.link_id)
                                val linkId: String = link.text.toString()

                                startIntent(linkId)
                            }
                            return true
                        }
                    })

                    val avatar: ImageView = view.findViewById(R.id.avatar)
                    Glide.with(this@RepositoryDetailsFragment)
                        .load(repositoryDetails!!.picture.avatar)
                        .into(avatar)

                    val tolBarView: Toolbar = view.findViewById(R.id.toolbar)
                    tolBarView.setTitle(repositoryDetails!!.nameDetails)

                    val name: TextView = view.findViewById(R.id.name)
                    name.setText(repositoryDetails!!.nameDetails)

                    val mojombo: TextView = view.findViewById(R.id.login)
                    mojombo.setText(repositoryDetails!!.picture.login)

                    val description: TextView = view.findViewById(R.id.description)
                    description.setText(repositoryDetails!!.descriptionDetails)

                    val link: TextView = view.findViewById(R.id.link_id)
                    link.setText(repositoryDetails!!.id)

                    val gradeDetail: TextView = view.findViewById(R.id.stargazers_сount)
                    gradeDetail.setText(repositoryDetails!!.gradeDetails)

                    val amountSharedId: TextView = view.findViewById(R.id.number_of_redirects)
                    amountSharedId.setText(repositoryDetails!!.repostDetails)

                    val numberMistakes: TextView = view.findViewById(R.id.number_of_mistakes)
                    numberMistakes.setText(repositoryDetails!!.mistakesDetails)

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
}