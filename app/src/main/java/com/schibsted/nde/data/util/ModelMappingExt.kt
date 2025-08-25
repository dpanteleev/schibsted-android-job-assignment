package com.schibsted.nde.data.util

import com.schibsted.nde.database.MealEntity
import com.schibsted.nde.model.MealResponse

fun MealResponse.toMealEntity(): MealEntity = MealEntity(
    id = this.idMeal,
    mealName = this.strMeal,
    category = this.strCategory,
    thumbnailUrl = this.strMealThumb,
    instructions = this.strInstructions
)