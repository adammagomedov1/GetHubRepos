package com.magomedov.githubrepos.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.databinding.FragmentSettingsBinding
import com.magomedov.githubrepos.viewmodels.SettingsViewModels

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var binding: FragmentSettingsBinding? = null

    val viewModel: SettingsViewModels by lazy {
        ViewModelProvider(this).get(SettingsViewModels::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        binding!!.settingsToolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                GitHubReposApplication.router.exit()
            }
        })

        viewModel.repositoryLiveData.observe(viewLifecycleOwner, object : Observer<String> {
            override fun onChanged(t: String?) {
                binding!!.settingsEditText.setText(t)
            }
        })

        binding!!.settingsButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                saveSettings()
            }
        })
        viewModel.loadSettings()
    }

    private fun saveSettings() {
        val repositoryList = binding!!.settingsEditText.text.toString()

        viewModel.onSaveSettings(repositoryList)

        val snackbar = Snackbar.make(
            requireView(),
            "Настройки сохранены",
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}