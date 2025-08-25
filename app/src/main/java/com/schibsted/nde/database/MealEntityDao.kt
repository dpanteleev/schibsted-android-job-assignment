package com.schibsted.nde.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MealEntityDao {
    @Query("SELECT * FROM meal ORDER BY id")
    fun getAll(): Flow<List<MealEntity>>

    @Query("SELECT * FROM meal WHERE id = :id")
    fun getById(id: String): MealEntity?

    @Query("SELECT * FROM meal WHERE name LIKE '%' || :query || '%'")
    fun findMeal(query: String): Flow<List<MealEntity>>

    @Upsert
    suspend fun upsertAll(meals: List<MealEntity>)
}
