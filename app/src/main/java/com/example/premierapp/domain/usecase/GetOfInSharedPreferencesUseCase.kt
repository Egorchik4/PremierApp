package com.example.premierapp.domain.usecase

import android.content.SharedPreferences

class GetOfInSharedPreferencesUseCase(private val sharedPreferences: SharedPreferences) {

	operator fun invoke(key: String): Boolean {
		return sharedPreferences.getBoolean(key, false)
	}
}