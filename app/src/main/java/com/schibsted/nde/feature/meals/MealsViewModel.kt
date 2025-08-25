package com.schibsted.nde.feature.meals

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schibsted.nde.data.MealsRepository
import com.schibsted.nde.database.MealEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val mealsRepository: MealsRepository,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _state = MutableStateFlow(MealsViewState(isLoading = true))

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<MealsViewState> =
        _query.flatMapLatest { currentQuery ->
            mealsRepository.getMeals(currentQuery).map { mealsList ->
                MealsViewState(
                    meals = mealsList,
                    filteredMeals = mealsList,
                    query = currentQuery,
                    isLoading = false
                )
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = MealsViewState(isLoading = true) // Initial state before any data
            )

    init {
        loadMeals()
    }

    fun loadMeals() {
        viewModelScope.launch {
            _state.emit(_state.value.copy(isLoading = true))
            mealsRepository.fetchMeals()
            _state.emit(_state.value.copy(isLoading = false))
        }
    }

    fun submitQuery(userInput: String?) {
        _query.value = userInput ?: ""
    }
}