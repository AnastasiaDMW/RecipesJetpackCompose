package com.example.recipesjetpackcompose.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesjetpackcompose.domain.interfaces.usecase.GetRecipeUseCase
import com.example.recipesjetpackcompose.domain.model.Result
import com.example.recipesjetpackcompose.presentation.TAG
import com.example.recipesjetpackcompose.presentation.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val recipeUseCase: GetRecipeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState

    private var amountRecipe: Int = 0

    fun handleEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.UpdateSearchTextField -> updateSearchText(event.recipe)
            HomeScreenEvent.GetRecipes -> getRecipes()
            HomeScreenEvent.ClearSearchTextField -> clearSearchText()
        }
    }

    private fun getRecipes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = recipeUseCase.execute(100 * amountRecipe)) {
                    is Result.Success -> {
                        amountRecipe += 1
                        _uiState.value = _uiState.value.copy(
                            recipes = recipeListDomainToPresenter(
                                result.data
                            )
                        )
                    }
                    is Result.TimeoutError -> onError(result.message)
                    is Result.UnknownError -> onError(result.message)
                    is Result.ConnectionError -> onError(result.message)
                    is Result.ServerError -> onError(message = "${result.message} ${result.errorCode}")
                }
            }
        }
    }

    private fun clearSearchText() {
        _uiState.value = _uiState.value.copy(
            isCloseIconVisible = true,
            searchText = ""
        )
    }

    private fun updateSearchText(recipe: String) {
        if (recipe.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                isCloseIconVisible = true,
                searchText = recipe
            )
        } else {
            _uiState.value = _uiState.value.copy(
                isCloseIconVisible = false,
                searchText = recipe
            )
        }
    }

    private fun onError(message: String) {
        Log.d(TAG, message)
    }

    private fun recipeListDomainToPresenter(
        recipes: List<com.example.recipesjetpackcompose.domain.model.Recipe>
    ): List<Recipe> {
        return recipes.map {
            Recipe(
                id = it.id,
                title = it.title,
                image = it.image,
                totalResults = it.totalResults
            )
        }
    }
}