package com.magomedov.githubrepos.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
class Favorites(

    @PrimaryKey
    @ColumnInfo(name = "login") val login: String,

    @ColumnInfo(name = "avatar_url") val avatar: String,

    @ColumnInfo(name = "nameProfile") val nameProfile: String,

    )
