package com.example.premierapp.presentation.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.premierapp.R
import com.example.premierapp.databinding.FragmentDetailsBinding
import com.example.premierapp.domain.entity.MealEntity
import com.example.premierapp.presentation.list.ListFragment.Companion.KEY_BUNDLE

class DetailsFragment : Fragment() {

	private lateinit var binding: FragmentDetailsBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentDetailsBinding.inflate(inflater)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val entity = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			arguments?.getSerializable(KEY_BUNDLE, MealEntity::class.java)
		} else {
			arguments?.getSerializable(KEY_BUNDLE) as? MealEntity
		}
		entity?.let { setData(it) }
		setListeners()
	}

	private fun setData(mealEntity: MealEntity) {
		with(binding) {
			Glide.with(strImageSource)
				.load(mealEntity.strImageSource)
				.circleCrop()
				.placeholder(R.mipmap.ic_launcher)
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.into(strImageSource)
			idMeal.text = mealEntity.idMeal
			strMeal.text = mealEntity.strMeal
			strCategory.text = mealEntity.strCategory
			strPrice.text = mealEntity.strPrice
			strInstructions.text = mealEntity.strInstructions
		}
	}

	private fun setListeners() {
		binding.buttonBack.setOnClickListener {
			findNavController().popBackStack()
		}
	}
}