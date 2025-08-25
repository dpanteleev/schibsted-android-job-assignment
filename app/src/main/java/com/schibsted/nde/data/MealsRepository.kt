package com.schibsted.nde.data

import com.schibsted.nde.api.BackendApi
import com.schibsted.nde.data.util.toMealEntity
import com.schibsted.nde.database.MealEntity
import com.schibsted.nde.database.MealEntityDao
import com.schibsted.nde.model.MealResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsRepository @Inject constructor(
    private val backendApi: BackendApi,
    private val mealEntityDao: MealEntityDao
) {
    suspend fun fetchMeals() {
        val meals = backendApi.getMeals().meals
        mealEntityDao.upsertAll(meals.map { it.toMealEntity() })
    }

    fun getMeals(query: String): Flow<List<MealEntity>> {
        return if (query.isBlank()) {
            mealEntityDao.getAll()
        } else {
            findMeal(query)
        }
    }

    fun getMealById(id: String): MealEntity? = mealEntityDao.getById(id)

    fun findMeal(query: String): Flow<List<MealEntity>> = mealEntityDao.findMeal(query)
}