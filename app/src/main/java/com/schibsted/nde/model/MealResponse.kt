package com.schibsted.nde.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealResponse(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strMealThumb: String,
    val strYoutube: String?,
    val strInstructions: String
) : Parcelable