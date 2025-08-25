package com.schibsted.nde.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal")
data class MealEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val mealName: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "thumb_url") val thumbnailUrl: String,
    @ColumnInfo(name = "instructions") val instructions: String
)
