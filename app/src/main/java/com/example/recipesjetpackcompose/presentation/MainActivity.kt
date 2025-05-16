package com.example.recipesjetpackcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.example.recipesjetpackcompose.R
import com.example.recipesjetpackcompose.presentation.screens.RecipeApp
import com.example.recipesjetpackcompose.presentation.theme.RecipesJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val backgroundColor = ContextCompat.getColor(this, R.color.background_color)
        setContent {
            RecipesJetpackComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(backgroundColor)
                ) {
                    RecipeApp()
                }
            }
        }
    }
}