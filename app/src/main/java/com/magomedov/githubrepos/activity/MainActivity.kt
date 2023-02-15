package com.magomedov.githubrepos.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.fragment.RepositoryListFragment

//Главный экран приложения
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    // Вызывается когда создаётся Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "был вызван onCreate")
        navigateFragment()

    }

    // Вызывается при унечтожении экран
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "был вызван onDestroy")

    }

    // Вызывается когда экран становится видимым
    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "был вызван onStart")

    }

    // Вызывается когда экран становится невидимым
    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "был вызван onStop")

    }

    private fun navigateFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_main_container, RepositoryListFragment())
        transaction.commit()
    }

}