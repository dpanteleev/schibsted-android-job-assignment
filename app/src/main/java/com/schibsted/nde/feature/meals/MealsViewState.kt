package com.schibsted.nde.feature.meals

import com.schibsted.nde.database.MealEntity

data class MealsViewState(
    val meals: List<MealEntity> = emptyList(),
    val filteredMeals: List<MealEntity> = emptyList(),
    val isLoading: Boolean = false,
    val query: String? = null
)