package com.example.masakapa.ui.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masakapa.R
import com.example.masakapa.data.model.Meal
import com.example.masakapa.databinding.ItemMealBinding

class MealAdapter(
    private val onItemClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private var meals: List<Meal> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int = meals.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }

    inner class MealViewHolder(private val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.tvMeal.text = meal.strMeal
            Glide.with(binding.ivMeal.context)
                .load(meal.strMealThumb)
                .error(R.drawable.placeholder)
                .into(binding.ivMeal)
            binding.tvArea.text = meal.strArea
            binding.tvCategory.text = meal.strCategory
            binding.root.setOnClickListener {
                onItemClick(meal)
            }
        }
    }
}