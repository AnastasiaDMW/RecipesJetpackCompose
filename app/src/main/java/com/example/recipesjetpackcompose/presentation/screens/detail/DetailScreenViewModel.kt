package com.example.recipesjetpackcompose.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesjetpackcompose.domain.interfaces.usecase.GetRecipeDetailUseCase
import com.example.recipesjetpackcompose.domain.model.DetailRecipe
import com.example.recipesjetpackcompose.presentation.model.ExtendedIngredients
import com.example.recipesjetpackcompose.presentation.model.RecipeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val recipeDetailUseCase: GetRecipeDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailScreenState())
    val uiState: StateFlow<DetailScreenState> = _uiState

    private val _showErrorSnackbar = MutableSharedFlow<Boolean>()
    val showErrorSnackbar: SharedFlow<Boolean> = _showErrorSnackbar

    fun handleEvent(event: DetailScreenEvent) {
        when (event) {
            is DetailScreenEvent.UpdateRecipeId -> getDetailDataForRecipe(event.recipeId)
            DetailScreenEvent.RetryLoading -> getDetailDataForRecipe(_uiState.value.currentRecipeId)
        }
    }

    private fun getDetailDataForRecipe(recipeId: Int) {
        _uiState.update {
            it.copy(
                isLoading = true,
                error = null,
                currentRecipeId = recipeId
            )
        }

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    recipeDetailUseCase.execute(recipeId)
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        recipeDetail = detailRecipeDomainToPresenter(response),
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
                _showErrorSnackbar.emit(true)
            }
        }
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
}