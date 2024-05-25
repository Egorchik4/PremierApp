package com.example.premierapp.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.premierapp.domain.usecase.GetOfInSharedPreferencesUseCase
import com.example.premierapp.domain.usecase.PutInSharedPreferencesUseCase
import com.example.premierapp.domain.usecase.ValidationUseCase
import com.example.premierapp.presentation.authorization.AuthorizationFragment.Companion.EMPTY_FIELD
import com.example.premierapp.presentation.authorization.AuthorizationFragment.Companion.ERROR_LENGTH
import com.example.premierapp.presentation.authorization.AuthorizationFragment.Companion.ERROR_MAIL
import com.example.premierapp.presentation.authorization.AuthorizationFragment.Companion.ERROR_STRING_CONTAIN_NUMBER
import com.example.premierapp.presentation.authorization.AuthorizationFragment.Companion.ERROR_STRING_LOWER_AND_UPPERCASE
import com.example.premierapp.presentation.authorization.AuthorizationFragment.Companion.KEY_AUTHORIZATION
import com.example.premierapp.presentation.authorization.AuthorizationFragment.Companion.NO_ERROR

class AuthorizationViewModel(
	private val validationUseCase: ValidationUseCase,
	private val putInSharedPreferencesUseCase: PutInSharedPreferencesUseCase,
	private val getOfInSharedPreferencesUseCase: GetOfInSharedPreferencesUseCase,
) : ViewModel() {

	private val _validationLoginMut = MutableLiveData<Error>(Error(null))
	val validationLoginLive: LiveData<Error> = _validationLoginMut

	private val _validationPasswordMut = MutableLiveData<Error>(Error(null))
	val validationPasswordLive: LiveData<Error> = _validationPasswordMut

	private val _navigateMut = MutableLiveData<Boolean>(false)
	val navigateLive: LiveData<Boolean> = _navigateMut

	fun validateLogin(text: String) {
		if (text.trim().isEmpty()) {
			_validationLoginMut.value = Error(EMPTY_FIELD)
		} else if (!validationUseCase.isValidEmail(text)) {
			_validationLoginMut.value = Error(ERROR_MAIL)
		} else {
			_validationLoginMut.value = Error(NO_ERROR)
		}
	}

	fun validatePassword(text: String) {
		return if (text.trim().isEmpty()) {
			_validationPasswordMut.value = Error(EMPTY_FIELD)
		} else if (text.length < 6) {
			_validationPasswordMut.value = Error(ERROR_LENGTH)
		} else if (!validationUseCase.isStringContainNumber(text)) {
			_validationPasswordMut.value = Error(ERROR_STRING_CONTAIN_NUMBER)
		} else if (!validationUseCase.isStringLowerAndUpperCase(text)) {
			_validationPasswordMut.value = Error(ERROR_STRING_LOWER_AND_UPPERCASE)
		} else {
			_validationPasswordMut.value = Error(NO_ERROR)
		}
	}

	fun authorization() {
		if (_validationLoginMut.value?.exception == NO_ERROR &&
			_validationPasswordMut.value?.exception == NO_ERROR
		) {
			_navigateMut.value = true
			putInStorage(value = true)
		}

	}

	private fun putInStorage(value: Boolean) {
		putInSharedPreferencesUseCase(KEY_AUTHORIZATION, value)
	}

	fun getOfInStorage() {
		val alreadyAuth = getOfInSharedPreferencesUseCase(KEY_AUTHORIZATION)
		_navigateMut.value = alreadyAuth
	}
}