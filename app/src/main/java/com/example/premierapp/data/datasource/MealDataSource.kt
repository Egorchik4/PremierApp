package com.example.premierapp.data.datasource

import com.example.premierapp.data.datasource.api.MealApi
import com.example.premierapp.data.datasource.model.MealModel

class MealDataSourceImpl(private val api: MealApi) : MealDataSource {

	override suspend fun getMeal(): MealModel = api.getPizzas()
}

interface MealDataSource {

	suspend fun getMeal(): MealModel
}