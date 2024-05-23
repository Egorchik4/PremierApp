package com.example.premierapp.data.datasource.model

data class MealModel(
	val meals: List<Meals>
)

data class Meals(
	val idMeal: String,
	val strMeal: String,
	val strCategory: String,
	val strInstructions: String,
	val strImageSource: String?
)