package com.example.premierapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class MultiViewModelFactory @Inject constructor(
	private val viewModelMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return viewModelMap.getValue(modelClass as Class<ViewModel>).get() as T
	}
}