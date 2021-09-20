package com.dorizu.masako.core.data

import com.dorizu.masako.core.data.source.local.LocalDataSource
import com.dorizu.masako.core.data.source.local.entity.RecipesDetailEntity
import com.dorizu.masako.core.data.source.remote.RemoteDataSource
import com.dorizu.masako.core.data.source.remote.network.ApiResponse
import com.dorizu.masako.core.data.source.remote.response.RecipesCategoryResponse
import com.dorizu.masako.core.data.source.remote.response.RecipesDetailResponse
import com.dorizu.masako.core.data.source.remote.response.RecipesResponse
import com.dorizu.masako.core.domain.model.Recipes
import com.dorizu.masako.core.domain.model.RecipesCategory
import com.dorizu.masako.core.domain.model.RecipesDetail
import com.dorizu.masako.core.domain.repository.IRecipesRepository
import com.dorizu.masako.core.utils.DataMapper
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IRecipesRepository {

    override fun getRecipesCategory(): Flowable<ResultState<List<RecipesCategory>>> =
        object : MapApiResponseToResultState<List<RecipesCategory>, List<RecipesCategoryResponse>>(){
            override fun createCall(): Flowable<ApiResponse<List<RecipesCategoryResponse>>>
                    = remoteDataSource.getRecipesCategory()

            override fun mapResponseToDomain(data: List<RecipesCategoryResponse>): List<RecipesCategory>
                    = DataMapper.mapListRecipeCategoryResponseToDomain(data)

        }.asFlowable()

    override fun getNewRecipes(): Flowable<ResultState<List<Recipes>>> =
        getRecipe(remoteDataSource.getNewRecipes())

    override fun getRecipeByCategory(key: String): Flowable<ResultState<List<Recipes>>> =
        getRecipe(remoteDataSource.getRecipesByCategory(key))

    override fun getRecipesBySearch(key: String): Flowable<ResultState<List<Recipes>>> =
        getRecipe(remoteDataSource.getRecipesBySearch(key))

    override fun getRecipesDetail(key: String): Flowable<ResultState<RecipesDetail>> =
        object : MapApiResponseToResultState<RecipesDetail, RecipesDetailResponse>(){
            override fun createCall(): Flowable<ApiResponse<RecipesDetailResponse>>
                    = remoteDataSource.getRecipesDetail(key)

            override fun mapResponseToDomain(data: RecipesDetailResponse): RecipesDetail
                    = DataMapper.mapRecipesDetailResponseToDomain(data, key)

        }.asFlowable()

    private fun getRecipe(client: Flowable<ApiResponse<List<RecipesResponse>>>): Flowable<ResultState<List<Recipes>>>  =
        object : MapApiResponseToResultState<List<Recipes>, List<RecipesResponse>>(){
            override fun createCall(): Flowable<ApiResponse<List<RecipesResponse>>>
                    = client

            override fun mapResponseToDomain(data: List<RecipesResponse>): List<Recipes>
                    = DataMapper.mapListRecipesResponseToDomain(data)

        }.asFlowable()

    override fun getSavedRecipes(): Flowable<ResultState<List<RecipesDetail>>> =
        object : MapApiResponseToResultState<List<RecipesDetail>, List<RecipesDetailEntity>>(){
            override fun createCall(): Flowable<ApiResponse<List<RecipesDetailEntity>>>
                = localDataSource.getSavedRecipes()

            override fun mapResponseToDomain(data: List<RecipesDetailEntity>): List<RecipesDetail>
                = DataMapper.mapListRecipesDetailEntityToDomain(data)

        }.asFlowable()

    override fun saveRecipes(recipes: RecipesDetail){
        val recipesEntity = DataMapper.mapRecipesDetailDomainToEntity(recipes)
        localDataSource.saveRecipes(recipesEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun deleteRecipesByKey(key: String){
        localDataSource.deleteRecipesByKey(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun checkIsSaved(key: String): Flowable<ResultState<RecipesDetail>> =
        object : MapApiResponseToResultState<RecipesDetail, RecipesDetailEntity>(){
            override fun createCall(): Flowable<ApiResponse<RecipesDetailEntity>>
                    = localDataSource.checkIsSaved(key)

            override fun mapResponseToDomain(data: RecipesDetailEntity): RecipesDetail
                    = DataMapper.mapRecipesDetailEntityToDomain(data)

        }.asFlowable()
}
