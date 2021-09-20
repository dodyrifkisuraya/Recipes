package com.dorizu.masako.core.data.source.remote.network

import com.dorizu.masako.core.data.source.remote.response.ResultRecipesCategoryResponse
import com.dorizu.masako.core.data.source.remote.response.ResultRecipesDetailResponse
import com.dorizu.masako.core.data.source.remote.response.ResultRecipesResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/api/recipes")
    fun getNewRecipes(): Flowable<ResultRecipesResponse>

    @GET("api/categorys/recipes")
    fun getRecipesCategory(): Flowable<ResultRecipesCategoryResponse>

    @GET("/api/categorys/recipes/{key}")
    fun getRecipesByCategory(@Path("key") key: String): Flowable<ResultRecipesResponse>

    @GET("/api/search/")
    fun getSearchRecipes(@Query("q") key: String): Flowable<ResultRecipesResponse>

    @GET("/api/recipe/{key}")
    fun getRecipesDetail(@Path("key") key : String): Flowable<ResultRecipesDetailResponse>
}