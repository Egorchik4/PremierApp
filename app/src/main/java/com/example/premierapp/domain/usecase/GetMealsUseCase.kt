package com.example.premierapp.domain.usecase

import com.example.premierapp.domain.entity.MealEntity
import com.example.premierapp.domain.repository.MealRepository

class GetMealsUseCase(
	private val repository: MealRepository
) : suspend () -> List<MealEntity> by repository::getMeals