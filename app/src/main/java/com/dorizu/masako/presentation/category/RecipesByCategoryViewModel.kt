package com.dorizu.masako.presentation.category

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
class RecipesByCategoryViewModel @Inject constructor(private val recipesUseCase: IRecipesUseCase) : ViewModel() {

    private val _recipes = MutableLiveData<ResultState<List<Recipes>>>()
    private val _category = MutableLiveData<ResultState<List<RecipesCategory>>>()

    fun getRecipesByCategory(key: String) {
        val disposable = when (key == "dessert") {
            true -> recipesUseCase.getRecipesBySearch(key)
            false -> recipesUseCase.getRecipeByCategory(key)
            }
            .subscribe { res ->
                _recipes.value = res
            }
        mCompositeDisposable.add(disposable)
    }

    fun getRecipesCategory(){
        val disposable = recipesUseCase.getRecipesCategory()
            .subscribe{res ->
                _category.value = res
                Timber.d(res.data.toString())
            }
        mCompositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }

    val recipes: LiveData<ResultState<List<Recipes>>> get() = _recipes
    val category: LiveData<ResultState<List<RecipesCategory>>> get() = _category

    companion object {
        private val mCompositeDisposable = CompositeDisposable()
    }
}