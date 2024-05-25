package com.example.premierapp.di

import androidx.lifecycle.ViewModel
import com.example.premierapp.domain.usecase.GetMealsUseCase
import com.example.premierapp.domain.usecase.GetOfInSharedPreferencesUseCase
import com.example.premierapp.domain.usecase.PutInSharedPreferencesUseCase
import com.example.premierapp.domain.usecase.ValidationUseCase
import com.example.premierapp.presentation.authorization.AuthorizationViewModel
import com.example.premierapp.presentation.list.ListViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {

	@Provides
	@[IntoMap ViewModelKey(AuthorizationViewModel::class)]
	fun provideAuthorizationViewModel(
		validationUseCase: ValidationUseCase,
		putInSharedPreferencesUseCase: PutInSharedPreferencesUseCase,
		getOfInSharedPreferencesUseCase: GetOfInSharedPreferencesUseCase
	): ViewModel {
		return AuthorizationViewModel(
			validationUseCase = validationUseCase,
			putInSharedPreferencesUseCase = putInSharedPreferencesUseCase,
			getOfInSharedPreferencesUseCase = getOfInSharedPreferencesUseCase,
		)
	}

	@Provides
	@[IntoMap ViewModelKey(ListViewModel::class)]
	fun provideListViewModel(
		getMealsUseCase: GetMealsUseCase,
		putInSharedPreferencesUseCase: PutInSharedPreferencesUseCase,
	): ViewModel {
		return ListViewModel(
			getMealsUseCase = getMealsUseCase,
			putInSharedPreferencesUseCase = putInSharedPreferencesUseCase,
		)
	}
}