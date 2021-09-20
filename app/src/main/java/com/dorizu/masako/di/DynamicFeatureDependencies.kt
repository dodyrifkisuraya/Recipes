package com.dorizu.masako.di

import com.dorizu.masako.core.domain.usecase.IRecipesUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DynamicFeatureDependencies {
    fun recipesUseCase(): IRecipesUseCase
}