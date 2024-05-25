package com.example.premierapp.presentation.list

import com.example.premierapp.domain.entity.MealEntity

sealed interface ListState {

	data object Initial : ListState

	data object Loading : ListState

	data class Content(val list: List<MealEntity>) : ListState

	data class Error(val errorMessage: String) : ListState
}