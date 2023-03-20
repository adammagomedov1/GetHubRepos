package com.magomedov.githubrepos.network

import com.magomedov.githubrepos.models.ProfileDetails
import com.magomedov.githubrepos.models.Repository
import com.magomedov.githubrepos.models.RepositoryDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHobService {
    @GET("repositories")
    suspend fun getRepositoryList(

    ): List<Repository>

    @GET("repositories/{id}")
    suspend fun getRepositoryDetails(
        @Path("id") repositoryId: Int
    ): RepositoryDetails

    @GET("users/{login}")
    suspend fun getRepositoryProfile(
        @Path("login") loginProfile: String?
    ): ProfileDetails

}