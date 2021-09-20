package com.dorizu.masako.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dorizu.masako.core.data.ResultState
import com.dorizu.masako.core.domain.model.Recipes
import com.dorizu.masako.core.domain.model.RecipesCategory
import com.dorizu.masako.core.domain.usecase.IRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val recipesUseCase: IRecipesUseCase): ViewModel() {

    init {
        getRecipesCategory()
        getNewRecipes()
    }

    private val _category = MutableLiveData<ResultState<List<RecipesCategory>>>()
    private val _newRecipe = MutableLiveData<ResultState<List<Recipes>>>()

    fun getRecipesCategory(){
        val disposable = recipesUseCase.getRecipesCategory()
            .subscribe{res ->
                _category.value = res
                Timber.d(res.data.toString())
            }
        mCompositeDisposable.add(disposable)
    }

    fun getNewRecipes(){
        val disposable = recipesUseCase.getNewRecipes()
            .subscribe{res ->
                _newRecipe.value = res
            }
        mCompositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }

    val category: LiveData<ResultState<List<RecipesCategory>>> get() = _category
    val newRecipes: LiveData<ResultState<List<Recipes>>> get() = _newRecipe

    companion object{
        private val mCompositeDisposable = CompositeDisposable()
    }
}