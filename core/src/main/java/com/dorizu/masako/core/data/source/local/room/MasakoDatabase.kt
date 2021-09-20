package com.dorizu.masako.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dorizu.masako.core.data.source.local.entity.RecipesDetailEntity

@Database(entities = [RecipesDetailEntity::class], version = 1, exportSchema = false)
abstract class MasakoDatabase: RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}