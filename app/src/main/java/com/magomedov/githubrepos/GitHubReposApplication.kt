package com.magomedov.githubrepos

import android.app.Application
import androidx.room.Room
import com.magomedov.githubrepos.database.AppDatabase
import com.magomedov.githubrepos.network.GitHobService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class GitHubReposApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "gitHub_repos"
        )
            .allowMainThreadQueries() // разрешаем
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        gitHubService = retrofit.create()

    }

    companion object {

        lateinit var appDatabase : AppDatabase

        lateinit var gitHubService: GitHobService

    }
}