package com.example.premierapp.data.repository

import com.example.premierapp.data.datasource.MealDataSource
import com.example.premierapp.data.datasource.mapper.toMealEntity
import com.example.premierapp.domain.entity.MealEntity
import com.example.premierapp.domain.repository.MealRepository

class MealRepositoryImpl(private val dataSource: MealDataSource) : MealRepository {

	override suspend fun getMeals(): List<MealEntity> =
		dataSource.getMeal().meals.toMealEntity()
}