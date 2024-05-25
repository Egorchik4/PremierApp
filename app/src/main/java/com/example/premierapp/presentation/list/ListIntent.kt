package com.example.premierapp.presentation.list

sealed interface ListIntent {

	data object LoadingList : ListIntent

	data object NavigateBack : ListIntent
}