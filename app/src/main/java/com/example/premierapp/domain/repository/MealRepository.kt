package com.example.premierapp.domain.repository

import com.example.premierapp.domain.entity.MealEntity

interface MealRepository {

	suspend fun getMeals(): List<MealEntity>
}