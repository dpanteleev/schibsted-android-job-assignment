package com.schibsted.nde.feature.details

import androidx.activity.result.launch
import androidx.compose.animation.core.copy
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schibsted.nde.data.MealsRepository
import com.schibsted.nde.database.MealEntity
import com.schibsted.nde.feature.common.NavRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel@Inject constructor(
    private val mealsRepository: MealsRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    //all and all, there should be proper state.
    //but in that case we are just fetching one record from DB, so it is not really necessary
    private val _state: MutableStateFlow<MealEntity?> = MutableStateFlow(null)
    val state: StateFlow<MealEntity?> = _state.asStateFlow()

    init {
        val mealId = savedStateHandle.get<String>(NavRoute.MealDetailsScreen.argMealId)
        if (mealId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                _state.update { mealsRepository.getMealById(mealId) }
            }
        }
    }
}