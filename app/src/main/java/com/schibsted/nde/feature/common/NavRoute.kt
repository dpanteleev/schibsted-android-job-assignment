package com.schibsted.nde.feature.common

sealed class NavRoute(val path: String) {
    object MealsScreen: NavRoute("meals")
    object MealDetailsScreen: NavRoute("meal_details")
}