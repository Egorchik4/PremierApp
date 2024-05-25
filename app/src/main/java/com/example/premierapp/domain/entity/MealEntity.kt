package com.example.premierapp.domain.entity

import java.io.Serializable

data class MealEntity(
	val idMeal: String,
	val strMeal: String,
	val strCategory: String,
	val strPrice: String,
	val strInstructions: String,
	val strImageSource: String?
) : Serializable