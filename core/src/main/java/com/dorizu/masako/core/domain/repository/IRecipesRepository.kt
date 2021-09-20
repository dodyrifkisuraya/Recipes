package com.dorizu.masako.core.domain.repository

import com.dorizu.masako.core.data.ResultState
import com.dorizu.masako.core.domain.model.Recipes
import com.dorizu.masako.core.domain.model.RecipesCategory
import com.dorizu.masako.core.domain.model.RecipesDetail
import io.reactivex.Flowable

interface IRecipesRepository {
    fun getNewRecipes(): Flowable<ResultState<List<Recipes>>>
    fun getRecipesCategory(): Flowable<ResultState<List<RecipesCategory>>>
    fun getRecipeByCategory(key: String): Flowable<ResultState<List<Recipes>>>
    fun getRecipesBySearch(key: String): Flowable<ResultState<List<Recipes>>>
    fun getRecipesDetail(key: String): Flowable<ResultState<RecipesDetail>>
    fun getSavedRecipes(): Flowable<ResultState<List<RecipesDetail>>>
    fun saveRecipes(recipes: RecipesDetail)
    fun deleteRecipesByKey(key: String)
    fun checkIsSaved(key: String): Flowable<ResultState<RecipesDetail>>
}