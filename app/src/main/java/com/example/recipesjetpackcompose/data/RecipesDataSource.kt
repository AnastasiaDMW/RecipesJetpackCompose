package com.example.recipesjetpackcompose.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recipesjetpackcompose.domain.interfaces.repository.RecipeRepository
import com.example.recipesjetpackcompose.domain.model.Recipe

class RecipesDataSource(
    private val recipeRepository: RecipeRepository
) : PagingSource<Int, Recipe>() {

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(100)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(100)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        return try {
            val offset = params.key ?: 0
            val response = recipeRepository.getRecipes(offset = offset)
            LoadResult.Page(
                data = response,
                prevKey = if (offset == 0) null else offset.minus(100),
                nextKey = if (response.isEmpty()) null else offset.plus(100),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}