package com.magomedov.githubrepos.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RepositoryDetails(

    @SerializedName("name")
    val nameDetails: String,

    @SerializedName("owner")
    val picture: Owner,

    @SerializedName("description")
    val descriptionDetails: String,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("stargazers_count")
    val gradeDetails: String,

    @SerializedName("forks_count")
    val repostDetails: String,

    @SerializedName("open_issues_count")
    val mistakesDetails: String,

    ) : Serializable
