package com.magomedov.githubrepos.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.magomedov.githubrepos.GitHubReposApplication
import com.magomedov.githubrepos.NomeScreen
import com.magomedov.githubrepos.R
import me.aartikov.alligator.NavigationContext

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
        //Выполняется переход на экран списка репозиториев
        GitHubReposApplication.navigator.replace(NomeScreen())
    }

    override fun onResume() {
        super.onResume()
        val navigationContext: NavigationContext =
            NavigationContext.Builder(this, GitHubReposApplication.navigatorFactory)
                .fragmentNavigation(supportFragmentManager, R.id.fragment_main_container)
                .build()
        GitHubReposApplication.navigationContextBinder.bind(navigationContext)
    }

    override fun onPause() {
        super.onPause()
        GitHubReposApplication.navigationContextBinder.unbind(this)
    }

    override fun onBackPressed() {
        GitHubReposApplication.navigator.goBack()
    }
}