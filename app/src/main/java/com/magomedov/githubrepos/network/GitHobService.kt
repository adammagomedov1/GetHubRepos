package com.magomedov.githubrepos.network

import com.magomedov.githubrepos.models.ProfileDetails
import com.magomedov.githubrepos.models.Repository
import com.magomedov.githubrepos.models.RepositoryDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHobService {
    @GET("repositories")
    fun getRepositoryList(): Call<List<Repository>>

    @GET("repositories/{id}")
    fun getRepositoryDetails(
        @Path("id") repositoryId: Int
    ): Call<RepositoryDetails>

    @GET("users/{login}")
    fun getRepositoryProfile(
        @Path("login") loginProfile: String?
    ): Call<ProfileDetails>

}