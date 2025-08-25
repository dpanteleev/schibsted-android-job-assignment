package com.schibsted.nde

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.schibsted.nde.feature.common.NavRoute
import com.schibsted.nde.feature.details.MealDetailsScreen
import com.schibsted.nde.feature.meals.MealsScreen
import com.schibsted.nde.model.MealResponse
import com.schibsted.nde.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppTheme {
                Surface(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.fillMaxSize(),

                    ) {
                    NavGraph(navController)
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = NavRoute.MealsScreen.path) {
        composable(route = NavRoute.MealsScreen.path) {
            MealsScreen(
                hiltViewModel(),
                navigateToDetails = { meal ->
                    navController.navigate(
                        NavRoute.MealDetailsScreen.createRoute(meal.id)
                    )
                }
            )
        }
        composable(
            route = NavRoute.MealDetailsScreen.path,
            arguments = listOf(navArgument(NavRoute.MealDetailsScreen.argMealId) {
                type = NavType.StringType
            })
        ) {
            MealDetailsScreen(
                viewModel = hiltViewModel(),
                onBackClick = navController::popBackStack
            )
        }
    }
}