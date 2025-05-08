package com.example.recipesjetpackcompose.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.example.recipesjetpackcompose.presentation.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState

    private val testData = listOf(
        Recipe(
            id = 715415,
            title = "Red Lentil Soup with Chicken and Turnips",
            image = "https://img.spoonacular.com/recipes/715415-312x231.jpg"
        ),
        Recipe(
            id = 716406,
            title = "Asparagus and Pea Soup: Real Convenience Food",
            image = "https://img.spoonacular.com/recipes/716406-312x231.jpg"
        ),
        Recipe(
            id = 644387,
            title = "Garlicky Kale",
            image = "https://img.spoonacular.com/recipes/644387-312x231.jpg"
        ),
        Recipe(
            id = 715446,
            title = "Slow Cooker Beef Stew",
            image = "https://img.spoonacular.com/recipes/715446-312x231.jpg"
        ),
    )

    init {
        initTestData()
    }

    fun handleEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.UpdateSearchTextField -> updateSearchText(event.recipe)
        }
    }

    private fun updateSearchText(recipe: String) {
        _uiState.value = _uiState.value.copy(
            searchText = recipe
        )
    }

    private fun initTestData() {
        _uiState.value = _uiState.value.copy(
            recipes = testData
        )
    }
}