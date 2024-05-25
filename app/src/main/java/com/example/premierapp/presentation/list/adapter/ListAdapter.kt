package com.example.premierapp.presentation.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.premierapp.domain.entity.MealEntity

class ListAdapter(
	private val onItemClick: (mealEntity: MealEntity) -> Unit
) : ListAdapter<MealEntity, ListViewHolder>(PaymentsDiffCallback()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		ListViewHolder.from(parent)

	override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
		holder.bind(getItem(position), onItemClick)
	}
}

class PaymentsDiffCallback : DiffUtil.ItemCallback<MealEntity>() {

	override fun areItemsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean {
		return oldItem.idMeal == newItem.idMeal
	}

	override fun areContentsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean {
		return oldItem == newItem
	}
}