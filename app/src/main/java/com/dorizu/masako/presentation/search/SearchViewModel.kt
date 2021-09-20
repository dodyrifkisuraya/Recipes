package com.dorizu.masako.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dorizu.masako.core.data.ResultState
import com.dorizu.masako.core.domain.model.Recipes
import com.dorizu.masako.core.domain.usecase.IRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val recipesUseCase: IRecipesUseCase): ViewModel() {

    private val _recipes = MutableLiveData<ResultState<List<Recipes>>>()

    fun getResultSearch(key: String){
        val disposable = recipesUseCase.getRecipesBySearch(key)
            .subscribe{res ->
                _recipes.value = res
            }
        mCompositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }

    val recipes: LiveData<ResultState<List<Recipes>>> get() = _recipes

    companion object{
        private val mCompositeDisposable = CompositeDisposable()
    }
}