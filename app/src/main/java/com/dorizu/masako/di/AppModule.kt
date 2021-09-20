package com.dorizu.masako.di

import com.dorizu.masako.core.domain.usecase.IRecipesUseCase
import com.dorizu.masako.core.domain.usecase.RecipesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideRecipesRepositoryUseCase(recipesUseCase: RecipesUseCase): IRecipesUseCase
}
