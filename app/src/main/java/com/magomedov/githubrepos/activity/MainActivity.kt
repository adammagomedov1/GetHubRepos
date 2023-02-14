package com.magomedov.githubrepos.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.fragment.RepositoryListFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "был вызван onCreate")
        navigateFragment()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "был вызван onDestroy")

    }

    // Вызывается когда экран становится видимым
    override fun onStart() {
        super.onStart()
        Log.d("MainActivity","был вызван onStart")

    }

    private fun navigateFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_main_container, RepositoryListFragment())
        transaction.commit()
    }

}