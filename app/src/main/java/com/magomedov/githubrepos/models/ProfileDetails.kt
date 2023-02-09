package com.magomedov.githubrepos.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProfileDetails(

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatar: String,

    @SerializedName("name")
    val nameProfile: String,

    @SerializedName("bio")
    val description: String,

    @SerializedName("location")
    val locationProfile: String,

    @SerializedName("email")
    val emailProfile: String,

    @SerializedName("followers")
    val followersProfile: String,

    @SerializedName("following")
    val followingProfile: String,

    @SerializedName("public_repos")
    val publicReposProfile: String

) : Serializable