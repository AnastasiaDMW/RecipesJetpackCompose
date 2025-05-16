package com.example.recipesjetpackcompose.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.recipesjetpackcompose.R
import com.example.recipesjetpackcompose.presentation.model.ExtendedIngredients
import com.example.recipesjetpackcompose.presentation.screens.util.ErrorScreen

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    recipeId: Int?,
    detailScreenViewModel: DetailScreenViewModel = hiltViewModel()
) {
    val state by detailScreenViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        detailScreenViewModel.showErrorSnackbar.collect {
            snackbarHostState.showSnackbar(
                message = state.error ?: "Error loading recipe",
                actionLabel = "Retry"
            )
        }
    }

    LaunchedEffect(recipeId) {
        recipeId?.let {
            detailScreenViewModel.handleEvent(
                event = DetailScreenEvent.UpdateRecipeId(recipeId = it)
            )
        }
    }

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(42.dp))
            }
        }

        state.error != null -> {
            ErrorScreen(
                retryAction = {
                    detailScreenViewModel.handleEvent(event = DetailScreenEvent.RetryLoading)
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        else -> {
            Scaffold(
                containerColor = colorResource(id = R.color.background_color),
                modifier = modifier
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .padding(top = 16.dp)
                            .height(248.dp),
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(state.recipeDetail.image)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(R.string.recipe_img)
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        text = state.recipeDetail.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(R.drawable.ic_gluten_free),
                            contentDescription = stringResource(R.string.gluten_free_icon)
                        )
                        Text(
                            text = stringResource(R.string.gluten_free_tv).format(
                                if (state.recipeDetail.glutenFree)
                                    stringResource(id = R.string.yes_tv) else stringResource(id = R.string.no_tv)
                            ),
                            fontSize = 18.sp
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(R.drawable.ic_vegan),
                            contentDescription = stringResource(R.string.ic_vegan)
                        )
                        Text(
                            text = stringResource(R.string.vegan_tv).format(
                                if (state.recipeDetail.vegan)
                                    stringResource(id = R.string.yes_tv) else stringResource(id = R.string.no_tv)
                            ),
                            fontSize = 18.sp
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(R.drawable.ic_time),
                            contentDescription = stringResource(R.string.ic_cooking_minutes)
                        )
                        Text(
                            text = stringResource(R.string.cooking_minutes_tv)
                                .format(state.recipeDetail.cookingMinutes),
                            fontSize = 18.sp
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(R.drawable.ic_health),
                            contentDescription = stringResource(R.string.ic_health_score)
                        )
                        Text(
                            text = stringResource(R.string.health_score_tv)
                                .format(state.recipeDetail.healthScore),
                            fontSize = 18.sp
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(R.drawable.ic_serving),
                            contentDescription = stringResource(R.string.ic_servings)
                        )
                        Text(
                            text = stringResource(R.string.servings_tv).format(state.recipeDetail.servings),
                            fontSize = 18.sp
                        )
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        text = stringResource(R.string.instruction_tv)
                            .format(removeHtmlTags(state.recipeDetail.instructions)),
                        fontSize = 20.sp
                    )

                    if (state.recipeDetail.extendedIngredients.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(R.string.ingredients_tv),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.recipeDetail.extendedIngredients) {
                                IngredientItem(it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun IngredientItem(extendedIngredients: ExtendedIngredients) {
    Column(
        modifier = Modifier
            .width(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(124.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .align(Alignment.Center),
                model = extendedIngredients.image,
                contentDescription = stringResource(R.string.ingredient_img),
                contentScale = ContentScale.FillHeight
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = extendedIngredients.name,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            textAlign = TextAlign.Center,
            text = "${extendedIngredients.amount} ${extendedIngredients.unit}"
        )
    }
}

private fun removeHtmlTags(text: String): String {
    return text.replace(Regex("</?\\w+[^>]*>"), "")
}