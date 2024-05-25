package com.example.premierapp.data.datasource.api

import com.example.premierapp.data.datasource.model.MealModel
import retrofit2.http.POST

interface MealApi {

	@POST("search.php?s")
	suspend fun getPizzas(): MealModel
}