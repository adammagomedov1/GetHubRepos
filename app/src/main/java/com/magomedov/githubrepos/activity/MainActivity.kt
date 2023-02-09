package com.magomedov.githubrepos.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.fragment.RepositoryListFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateFragment()
    }

    private fun navigateFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_main_container, RepositoryListFragment())
        transaction.commit()
    }
}