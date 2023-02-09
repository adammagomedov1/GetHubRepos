package com.magomedov.githubrepos.network

import androidx.room.*
import com.magomedov.githubrepos.models.Favorites

@Dao
interface FavoriteDao {

    @Query("select * from favorites")
    fun getAllAuthors(): List<Favorites>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthors(favorite: Favorites)

    @Delete
    fun deleteAuthors(favorite: Favorites)

}