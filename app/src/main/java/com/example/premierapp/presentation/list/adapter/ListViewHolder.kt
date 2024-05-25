package com.example.premierapp.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.premierapp.R
import com.example.premierapp.databinding.ListItemBinding
import com.example.premierapp.domain.entity.MealEntity

class ListViewHolder(
	private val binding: ListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

	companion object {

		fun from(parent: ViewGroup): ListViewHolder {
			val inflater = LayoutInflater.from(parent.context)
			val binding = ListItemBinding.inflate(inflater, parent, false)
			return ListViewHolder(binding)
		}
	}

	fun bind(
		mealEntity: MealEntity,
		onItemClick: (mealEntity: MealEntity) -> Unit
	) {
		with(binding) {
			Glide.with(itemView)
				.load(mealEntity.strImageSource)
				.circleCrop()
				.placeholder(R.mipmap.ic_launcher)
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.into(imageView)
			textMeal.text = mealEntity.strMeal
			textCategory.text = mealEntity.strCategory
			textPrice.text = mealEntity.strPrice
			root.setOnClickListener { onItemClick(mealEntity) }
		}
	}
}