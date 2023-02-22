package com.magomedov.githubrepos.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.databinding.FragmentAboutAppBinding

class AboutAppFragment: Fragment(R.layout.fragment_about_app) {
  private var binding : FragmentAboutAppBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutAppBinding.bind(view)

        binding!!.toolbarAboutApp.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}