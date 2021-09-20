package com.dorizu.masako.core.data.source.local

import android.annotation.SuppressLint
import android.util.Log
import com.dorizu.masako.core.data.source.local.entity.RecipesDetailEntity
import com.dorizu.masako.core.data.source.local.room.RecipesDao
import com.dorizu.masako.core.data.source.remote.RemoteDataSource
import com.dorizu.masako.core.data.source.remote.network.ApiResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao){

    @SuppressLint("CheckResult")
    fun getSavedRecipes(): Flowable<ApiResponse<List<RecipesDetailEntity>>>{
        val results = PublishSubject.create<ApiResponse<List<RecipesDetailEntity>>>()

        val client = recipesDao.getSavedRecipes()
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({response ->
                if (response.isNotEmpty()){
                    results.onNext(ApiResponse.Success(response))
                }else{
                    results.onNext(ApiResponse.Empty)
                }
            }, {error ->
                results.onNext(ApiResponse.Error(error.message.toString()))
                Log.e(RemoteDataSource.TAG, error.toString())
            })

        return results.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun saveRecipes(recipes: RecipesDetailEntity) = recipesDao.saveRecipes(recipes)

    fun deleteRecipesByKey(key: String) = recipesDao.deleteRecipesByKey(key)

    @SuppressLint("CheckResult")
    fun checkIsSaved(key: String): Flowable<ApiResponse<RecipesDetailEntity>>{
        val results = PublishSubject.create<ApiResponse<RecipesDetailEntity>>()

        val client = recipesDao.checkIsSaved(key)
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({response ->
                results.onNext(ApiResponse.Success(response))
            }, {error ->
                results.onNext(ApiResponse.Error(error.message.toString()))
                Log.e(RemoteDataSource.TAG, error.toString())
            })

        return results.toFlowable(BackpressureStrategy.BUFFER)
    }
}