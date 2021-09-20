package com.dorizu.masako.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dorizu.masako.core.data.source.local.entity.RecipesDetailEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface RecipesDao {

    @Query("SELECT * FROM recipes")
    fun getSavedRecipes(): Flowable<List<RecipesDetailEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRecipes(recipes: RecipesDetailEntity): Completable

    @Query("DELETE FROM recipes WHERE `key` = :keyword")
    fun deleteRecipesByKey(keyword: String): Completable

    @Query("SELECT * FROM recipes WHERE `key` = :key")
    fun checkIsSaved(key: String): Flowable<RecipesDetailEntity>
}