package com.example.premierapp.presentation.authorization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.premierapp.app.App
import com.example.premierapp.R
import com.example.premierapp.databinding.FragmentAuthorizationBinding
import com.example.premierapp.di.MultiViewModelFactory
import javax.inject.Inject

class AuthorizationFragment : Fragment() {

	companion object {
		const val KEY_AUTHORIZATION = "KEY_AUTHORIZATION"

		const val NO_ERROR = ""
		const val EMPTY_FIELD = "Required Field!"
		const val ERROR_MAIL = "Invalid Email!"
		const val ERROR_LENGTH = "Password can't be less than 6"
		const val ERROR_STRING_CONTAIN_NUMBER = "Required at least 1 digit"
		const val ERROR_STRING_LOWER_AND_UPPERCASE = "Password must contain upper and lower case letters"
		const val FLAGS = 0
	}

	private lateinit var binding: FragmentAuthorizationBinding

	@Inject
	lateinit var viewModelFactory: MultiViewModelFactory
	private val viewModel by viewModels<AuthorizationViewModel> { viewModelFactory }

	override fun onAttach(context: Context) {
		super.onAttach(context)
		(requireContext().applicationContext as App).appComponent.inject(this)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentAuthorizationBinding.inflate(inflater)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.getOfInStorage()
		setObservers()
		setListeners()
	}

	private fun setObservers() {
		with(viewModel) {
			validationLoginLive.observe(viewLifecycleOwner) {
				binding.editTextLayoutLogin.error = it.exception
			}
			validationPasswordLive.observe(viewLifecycleOwner) {
				binding.editTextLayoutPassword.error = it.exception
			}
			navigateLive.observe(viewLifecycleOwner) {
				if (it) {
					navigateToListFragment()
				}
			}
		}
	}

	private fun setListeners() {
		with(binding) {
			editTextLogin.addTextChangedListener {
				viewModel.validateLogin(it.toString())
			}
			editTextPassword.addTextChangedListener {
				viewModel.validatePassword(it.toString())
			}
			buttonAuthorization.setOnClickListener {
				hideKeyboard(it)
				viewModel.authorization()
			}
		}

	}

	private fun navigateToListFragment() {
		findNavController().navigate(R.id.action_authorizationFragment_to_listFragment)
	}

	private fun hideKeyboard(view: View) {
		val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
		imm?.hideSoftInputFromWindow(view.windowToken, FLAGS)
	}
}