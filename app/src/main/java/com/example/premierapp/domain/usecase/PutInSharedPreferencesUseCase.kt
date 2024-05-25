package com.example.premierapp.domain.usecase

import android.content.SharedPreferences
import androidx.core.content.edit

class PutInSharedPreferencesUseCase(private val sharedPreferences: SharedPreferences) {

	operator fun invoke(key: String, data: Boolean) {
		sharedPreferences.edit {
			putBoolean(key, data).apply()
		}
	}
}