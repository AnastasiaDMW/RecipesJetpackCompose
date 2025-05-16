package com.example.recipesjetpackcompose.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.recipesjetpackcompose.data.RecipesDataSource
import com.example.recipesjetpackcompose.data.SearchRecipesDataSource
import com.example.recipesjetpackcompose.domain.interfaces.repository.RecipeRepository
import com.example.recipesjetpackcompose.presentation.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val basePager = Pager(
        PagingConfig(
            pageSize = 100,
            initialLoadSize = 100,
            prefetchDistance = 50
        )
    ) {
        RecipesDataSource(repository)
    }.flow.cachedIn(viewModelScope)

    val recipesPager = _searchQuery
        .debounce(500L)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                basePager
            } else {
                Pager(
                    PagingConfig(pageSize = 100)
                ) {
                    SearchRecipesDataSource(repository, query)
                }.flow
            }
        }
        .map { pagingData ->
            pagingData.map { domainRecipe ->
                Recipe(
                    id = domainRecipe.id,
                    title = domainRecipe.title,
                    image = domainRecipe.image,
                    totalResults = domainRecipe.totalResults
                )
            }
        }
        .cachedIn(viewModelScope)

    init {
        loadInitialData()
    }

    fun handleEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.UpdateSearchTextField -> updateSearchText(event.recipe)
            HomeScreenEvent.ClearSearchTextField -> clearSearchText()
        }
    }

    private fun loadInitialData() {
        _uiState.update { it.copy(isLoading = true) }
    }

    private fun clearSearchText() {
        _uiState.value = _uiState.value.copy(
            isCloseIconVisible = true
        )
        _searchQuery.value = ""
    }

    private fun updateSearchText(recipe: String) {
        if (recipe.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                isCloseIconVisible = true
            )
        } else {
            _uiState.value = _uiState.value.copy(
                isCloseIconVisible = false
            )
        }
        _searchQuery.value = recipe
    }
}