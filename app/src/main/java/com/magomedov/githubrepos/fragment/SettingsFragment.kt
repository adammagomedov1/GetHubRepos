package com.magomedov.githubrepos.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var binding : FragmentSettingsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        binding!!.settingsToolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })

        val sharedPreferences = requireContext().getSharedPreferences(
            "git_hub_preferences",
            Context.MODE_PRIVATE
        )

        val repositoriesEditor: String? =
            sharedPreferences.getString("repositories", null)
        binding!!.settingsEditText.setText(repositoriesEditor)

        binding!!.settingsButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                saveSettings()
            }
        })
    }

    private fun saveSettings() {
        val sharedPreferences = requireContext().getSharedPreferences(
            "git_hub_preferences",
            Context.MODE_PRIVATE
        )
        val repositoryList = binding!!.settingsEditText.text.toString()

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("repositories", repositoryList)
        editor.apply()

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