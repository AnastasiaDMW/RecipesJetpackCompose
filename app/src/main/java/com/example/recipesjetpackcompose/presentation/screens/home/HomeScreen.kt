package com.example.recipesjetpackcompose.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.recipesjetpackcompose.R
import com.example.recipesjetpackcompose.presentation.model.Recipe

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (Int) -> Unit,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val searchQuery by homeScreenViewModel.searchQuery.collectAsState()
    val state by homeScreenViewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val recipeList = homeScreenViewModel.recipesPager.collectAsLazyPagingItems()

    Scaffold(
        containerColor = colorResource(id = R.color.background_color),
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                trailingIcon = {
                    if (state.isCloseIconVisible) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.ic_search)
                        )
                    } else {
                        Icon(
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    homeScreenViewModel.handleEvent(
                                        event = HomeScreenEvent.ClearSearchTextField
                                    )
                                },
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.ic_close)
                        )
                    }

                },
                singleLine = true,
                value = searchQuery,
                onValueChange = {
                    homeScreenViewModel.handleEvent(
                        event = HomeScreenEvent.UpdateSearchTextField(
                            recipe = it
                        )
                    )
                },
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        // TODO: Добавить событие поиска рецептов
                    }
                ),
                label = {
                    Text(
                        text = stringResource(R.string.search),
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    count = recipeList.itemCount,
                    key = { index -> recipeList[index]?.id ?: index }
                ) { index ->
                    val item = recipeList[index]
                    item?.let {
                        RecipeItem(
                            recipe = it,
                            onClickToRecipe = onRecipeClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RecipeItem(
    recipe: Recipe,
    onClickToRecipe: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClickToRecipe(recipe.id)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .height(248.dp),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(recipe.image)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.recipe_img)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = recipe.title,
            fontSize = 20.sp,
            color = Color.Black
        )
    }
}