package com.schibsted.nde.feature.common

sealed class NavRoute(val path: String) {
    object MealsScreen: NavRoute("meals")

    object MealDetailsScreen {
        private const val ROUTE_PREFIX = "meal_details/"
        const val path = "$ROUTE_PREFIX{mealId}" // Define argument in the path
        const val argMealId = "mealId" // Key for the argument

        fun createRoute(mealId: String) = "$ROUTE_PREFIX$mealId"
    }
}