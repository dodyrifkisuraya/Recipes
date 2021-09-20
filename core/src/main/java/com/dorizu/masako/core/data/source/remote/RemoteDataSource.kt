package com.dorizu.masako.core.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import com.dorizu.masako.core.data.source.remote.network.ApiResponse
import com.dorizu.masako.core.data.source.remote.network.ApiService
import com.dorizu.masako.core.data.source.remote.response.RecipesCategoryResponse
import com.dorizu.masako.core.data.source.remote.response.RecipesDetailResponse
import com.dorizu.masako.core.data.source.remote.response.RecipesResponse
import com.dorizu.masako.core.data.source.remote.response.ResultRecipesResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    companion object{
        const val TAG = "RemoteDataSource"
    }

    fun getNewRecipes(): Flowable<ApiResponse<List<RecipesResponse>>>
        = getRecipe(apiService.getNewRecipes())

    @SuppressLint("CheckResult")
    fun getRecipesCategory(): Flowable<ApiResponse<List<RecipesCategoryResponse>>> {
        val resultDataCategory = PublishSubject.create<ApiResponse<List<RecipesCategoryResponse>>>()

        val client = apiService.getRecipesCategory()

        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({response ->
                val dataCategory = response.results
                resultDataCategory.onNext(if (dataCategory.isNotEmpty()) ApiResponse.Success(dataCategory) else ApiResponse.Empty)
            }, {error ->
                resultDataCategory.onNext(ApiResponse.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        return resultDataCategory.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getRecipesByCategory(key: String): Flowable<ApiResponse<List<RecipesResponse>>>
        = getRecipe(apiService.getRecipesByCategory(key))

    fun getRecipesBySearch(key: String): Flowable<ApiResponse<List<RecipesResponse>>>
        = getRecipe(apiService.getSearchRecipes(key))

    @SuppressLint("CheckResult")
    private fun getRecipe(client: Flowable<ResultRecipesResponse>): Flowable<ApiResponse<List<RecipesResponse>>>{
        val resultData = PublishSubject.create<ApiResponse<List<RecipesResponse>>>()
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({response ->
                val recipes = response.results
                resultData.onNext(if (recipes.isNotEmpty()) ApiResponse.Success(recipes) else ApiResponse.Empty)
            },{ error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e(TAG, error.message.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getRecipesDetail(key: String): Flowable<ApiResponse<RecipesDetailResponse>>{
        val results = PublishSubject.create<ApiResponse<RecipesDetailResponse>>()

        val client = apiService.getRecipesDetail(key)
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({response ->
                val data = response.results
                results.onNext(ApiResponse.Success(data))
            }, {error ->
                results.onNext(ApiResponse.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        return results.toFlowable(BackpressureStrategy.BUFFER)
    }
}