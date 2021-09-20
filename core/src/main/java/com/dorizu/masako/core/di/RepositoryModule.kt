package com.dorizu.masako.core.di

import com.dorizu.masako.core.data.RecipesRepository
import com.dorizu.masako.core.domain.repository.IRecipesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(recipesRepository: RecipesRepository): IRecipesRepository

}