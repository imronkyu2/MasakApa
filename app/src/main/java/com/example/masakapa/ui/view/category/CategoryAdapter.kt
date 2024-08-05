package com.example.masakapa.ui.view.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masakapa.data.model.Category
import com.example.masakapa.databinding.ItemGridCategoriesBinding

class CategoryAdapter(
    private val categories: List<Category>,
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: ItemGridCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemGridCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.tvCategory.text = category.strCategory
        Glide.with(holder.itemView.context)
            .load(category.strCategoryThumb)
            .into(holder.binding.ivCategory)

        holder.itemView.setOnClickListener { onItemClick(category) }
    }

    override fun getItemCount(): Int = categories.size
}
