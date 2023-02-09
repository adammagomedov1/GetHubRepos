package com.magomedov.githubrepos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.magomedov.githubrepos.models.Favorites
import com.magomedov.githubrepos.network.FavoriteDao

@Database(
    entities = arrayOf(Favorites::class),
    version = 1,
//    autoMigrations = arrayOf(AutoMigration(from = 2, to = 3))

)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoriteDao

}