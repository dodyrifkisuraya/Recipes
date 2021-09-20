package com.dorizu.masako.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dorizu.masako.core.data.ResultState
import com.dorizu.masako.core.domain.model.RecipesDetail
import com.dorizu.masako.core.domain.usecase.IRecipesUseCase
import io.reactivex.disposables.CompositeDisposable

class SavedRecipesViewModel(private val recipesUseCase: IRecipesUseCase): ViewModel() {

    init {
        getSavedRecipes()
    }

    private val _recipesSaved = MutableLiveData<ResultState<List<RecipesDetail>>>()

    fun getSavedRecipes(){
        val disposable = recipesUseCase.getSavedRecipes()
            .subscribe{res ->
                _recipesSaved.value = res
            }
        mCompositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }

    val recipesSaved: LiveData<ResultState<List<RecipesDetail>>> get() = _recipesSaved

    companion object{
        private val mCompositeDisposable = CompositeDisposable()
    }
}