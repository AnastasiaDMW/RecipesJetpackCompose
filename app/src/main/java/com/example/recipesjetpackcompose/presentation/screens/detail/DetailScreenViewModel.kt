package com.example.recipesjetpackcompose.presentation.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesjetpackcompose.domain.interfaces.usecase.GetRecipeDetailUseCase
import com.example.recipesjetpackcompose.domain.model.DetailRecipe
import com.example.recipesjetpackcompose.domain.model.Result
import com.example.recipesjetpackcompose.presentation.TAG
import com.example.recipesjetpackcompose.presentation.model.ExtendedIngredients
import com.example.recipesjetpackcompose.presentation.model.RecipeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val recipeDetailUseCase: GetRecipeDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailScreenState())
    val uiState: StateFlow<DetailScreenState> = _uiState

    fun handleEvent(event: DetailScreenEvent) {
        when (event) {
            is DetailScreenEvent.UpdateRecipeId -> getDetailDataForRecipe(event.recipeId)
        }
    }

    private fun getDetailDataForRecipe(recipeId: Int) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                when (val result = recipeDetailUseCase.execute(recipeId)) {
//                    is Result.Success -> {
//                        _uiState.value = _uiState.value.copy(
//                            isLoading = false,
//                            recipeDetail = detailRecipeDomainToPresenter(result.data)
//                        )
//                    }
//                    is Result.ConnectionError -> onError(result.message)
//                    is Result.ServerError -> onError(message = "${result.message} ${result.errorCode}")
//                    is Result.TimeoutError -> onError(result.message)
//                    is Result.UnknownError -> onError(result.message)
//                }
//            }
//        }
    }

    private fun detailRecipeDomainToPresenter(detail: DetailRecipe): RecipeDetail {
        return RecipeDetail(
            id = detail.id,
            image = detail.image,
            title = detail.title,
            glutenFree = detail.glutenFree,
            vegan = detail.vegan,
            cookingMinutes = detail.cookingMinutes,
            healthScore = detail.healthScore,
            servings = detail.servings,
            instructions = detail.instructions,
            extendedIngredients = extendedIngredientsDomainToPresenter(detail.extendedIngredients)
        )
    }

    private fun extendedIngredientsDomainToPresenter(
        ingredient: List<com.example.recipesjetpackcompose.domain.model.ExtendedIngredients>
    ): List<ExtendedIngredients> {
        return ingredient.map {
            ExtendedIngredients(
                id = it.id,
                image = it.image,
                name = it.name,
                amount = it.amount,
                unit = it.unit
            )
        }
    }

    private fun onError(message: String) {
        Log.d(TAG, message)
    }
}