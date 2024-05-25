package com.example.premierapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierapp.domain.usecase.GetMealsUseCase
import com.example.premierapp.domain.usecase.PutInSharedPreferencesUseCase
import com.example.premierapp.presentation.authorization.AuthorizationFragment.Companion.KEY_AUTHORIZATION
import kotlinx.coroutines.launch

class ListViewModel(
	private val getMealsUseCase: GetMealsUseCase,
	private val putInSharedPreferencesUseCase: PutInSharedPreferencesUseCase,
) : ViewModel() {

	private val _listStateMut = MutableLiveData<ListState>(ListState.Initial)
	val listStateLive: LiveData<ListState> = _listStateMut

	private val _navigationMut = MutableLiveData<Boolean>(false)
	val navigationLive: LiveData<Boolean> = _navigationMut

	fun setIntent(intent: ListIntent) {
		when (intent) {
			is ListIntent.LoadingList  -> loadMeals()
			is ListIntent.NavigateBack -> navigateBack()
		}
	}

	private fun loadMeals() {
		_listStateMut.value = ListState.Loading
		viewModelScope.launch {
			try {
				val list = getMealsUseCase()
				_listStateMut.value = ListState.Content(list = list)
			} catch (e: Exception) {
				_listStateMut.value = ListState.Error(errorMessage = e.toString())
			}
		}
	}

	private fun navigateBack() {
		_navigationMut.value = true
		putInSharedPreferencesUseCase(KEY_AUTHORIZATION, false)
	}
}