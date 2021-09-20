package com.dorizu.masako.presentation.detail.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dorizu.masako.core.data.ResultState
import com.dorizu.masako.core.domain.model.RecipesDetail
import com.dorizu.masako.core.domain.usecase.IRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class DetailRecipesViewModel @Inject constructor(private val recipesUseCase: IRecipesUseCase): ViewModel() {

    private val _recipesDetail = MutableLiveData<ResultState<RecipesDetail>>()
    private val _isSaved = MutableLiveData<Boolean>()

    fun getRecipesDetail(key: String){
        val disposable = recipesUseCase.getRecipesDetail(key)
            .subscribe{res ->
                _recipesDetail.value = res
            }
        mCompositeDisposable.add(disposable)
    }

    fun checkIsSavedRecipes(key: String){
        val disposable = recipesUseCase.checkIsSaved(key)
            .subscribe{ res ->
                _isSaved.value = when (res.data != null){
                    false -> false
                    true -> true
                }
            }
        mCompositeDisposable.add(disposable)
    }

    fun saveRecipes(recipes: RecipesDetail){
        recipesUseCase.saveRecipes(recipes)
    }

    fun deleteRecipes(key: String){
        recipesUseCase.deleteRecipesByKey(key)
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }

    val recipesDetail: LiveData<ResultState<RecipesDetail>> get() = _recipesDetail
    val isSaved: LiveData<Boolean> get() = _isSaved

    companion object{
        private val mCompositeDisposable = CompositeDisposable()
    }
}