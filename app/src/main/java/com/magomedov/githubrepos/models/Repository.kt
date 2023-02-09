package com.magomedov.githubrepos.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Repository(
    @SerializedName("name")
    val name: String,

    @SerializedName("owner")
    val owner: Owner,

    @SerializedName("description")
    val description: String,

    @SerializedName("id")
    val id : Int

) : Serializable