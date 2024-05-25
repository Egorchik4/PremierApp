package com.example.premierapp.presentation.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.premierapp.R
import com.example.premierapp.app.App
import com.example.premierapp.databinding.FragmentListBinding
import com.example.premierapp.di.MultiViewModelFactory
import com.example.premierapp.domain.entity.MealEntity
import com.example.premierapp.presentation.list.adapter.ListAdapter
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ListFragment : Fragment() {

	companion object {

		const val KEY_BUNDLE = "KEY_BUNDLE"
	}

	private lateinit var binding: FragmentListBinding

	@Inject
	lateinit var viewModelFactory: MultiViewModelFactory
	private val viewModel by viewModels<ListViewModel> { viewModelFactory }
	private lateinit var adapter: ListAdapter

	override fun onAttach(context: Context) {
		super.onAttach(context)
		(requireContext().applicationContext as App).appComponent.inject(this)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.setIntent(ListIntent.LoadingList)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentListBinding.inflate(inflater)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setAdapter()
		setListener()
		setObservers()
	}

	private fun setAdapter() {
		adapter = ListAdapter(::navigateToDetailsFragment)
		binding.recyclerView.adapter = adapter
	}

	private fun setListener() {
		with(binding) {
			buttonBack.setOnClickListener {
				viewModel.setIntent(ListIntent.NavigateBack)
			}
			swipeRefreshLayout.setOnRefreshListener {
				viewModel.setIntent(ListIntent.LoadingList)
				binding.swipeRefreshLayout.isRefreshing = false
			}
		}
	}

	private fun setObservers() {
		viewModel.listStateLive.observe(viewLifecycleOwner) {
			when (it) {
				is ListState.Initial -> {}
				is ListState.Loading -> { handleLoading() }
				is ListState.Content -> { handleContent(it) }
				is ListState.Error   -> { handleError(it.errorMessage) }
			}
		}
		viewModel.navigationLive.observe(viewLifecycleOwner) {
			if (it) {
				findNavController().popBackStack()
			}
		}
	}

	private fun handleLoading() {
		with(binding) {
			progressBar.visibility = View.VISIBLE
			recyclerView.visibility = View.GONE
		}
	}

	private fun handleContent(listState: ListState.Content) {
		with(binding) {
			progressBar.visibility = View.GONE
			recyclerView.visibility = View.VISIBLE
		}
		adapter.submitList(listState.list)
	}

	private fun handleError(message: String) {
		with(binding) {
			progressBar.visibility = View.GONE
			recyclerView.visibility = View.GONE
		}
		showSnackBar(binding.materialTextView, message)
	}

	private fun showSnackBar(view: View, message: String) {
		context?.let {
			Snackbar.make(view, message, Snackbar.LENGTH_LONG)
				.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
				.setBackgroundTint(it.getColor(R.color.main_color))
				.show()
		}
	}

	private fun navigateToDetailsFragment(mealEntity: MealEntity) {
		findNavController().navigate(R.id.action_listFragment_to_detailsFragment, bundleOf(KEY_BUNDLE to mealEntity))
	}
}