package com.dorizu.masako.core.domain.usecase

import com.dorizu.masako.core.domain.model.RecipesDetail
import com.dorizu.masako.core.domain.repository.IRecipesRepository
import javax.inject.Inject

class RecipesUseCase @Inject constructor(private val recipesRepository: IRecipesRepository): IRecipesUseCase {

    override fun getNewRecipes()= recipesRepository.getNewRecipes()

    override fun getRecipesCategory() = recipesRepository.getRecipesCategory()

    override fun getRecipeByCategory(key: String) = recipesRepository.getRecipeByCategory(key)

    override fun getRecipesBySearch(key: String) = recipesRepository.getRecipesBySearch(key)

    override fun getRecipesDetail(key: String) = recipesRepository.getRecipesDetail(key)

    override fun getSavedRecipes() = recipesRepository.getSavedRecipes()

    override fun saveRecipes(recipes: RecipesDetail) = recipesRepository.saveRecipes(recipes)

    override fun deleteRecipesByKey(key: String) = recipesRepository.deleteRecipesByKey(key)

    override fun checkIsSaved(key: String) = recipesRepository.checkIsSaved(key)
}