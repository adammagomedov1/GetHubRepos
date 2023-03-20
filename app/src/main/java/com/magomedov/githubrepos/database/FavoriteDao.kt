package com.magomedov.githubrepos.database

import androidx.room.*
import com.magomedov.githubrepos.models.Favorites

@Dao
interface FavoriteDao {

    @Query("select * from favorites")
    suspend fun getAllAuthors(): List<Favorites>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthors(favorite: Favorites)

    @Delete
    suspend fun deleteAuthors(favorite: Favorites)

}