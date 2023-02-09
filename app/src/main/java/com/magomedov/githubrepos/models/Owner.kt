package com.magomedov.githubrepos.models

import com.google.gson.annotations.SerializedName

class Owner(

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatar: String
)


