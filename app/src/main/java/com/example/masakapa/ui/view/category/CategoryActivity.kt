package com.example.masakapa.ui.view.category

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.masakapa.R
import com.example.masakapa.databinding.ActivityCategoryBinding
import com.example.masakapa.ui.view.filter.FilterActivity
import com.example.masakapa.ui.viewmodel.MealViewModel
import com.example.masakapa.ui.viewmodel.MealViewModelFactory
import com.example.masakapa.utils.Resource

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private val viewModel: MealViewModel by viewModels { MealViewModelFactory(this) }
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        setupRecyclerView()
        setupObservers()
    }

    private fun initListener() {
        binding.toolbarMenu.tvTitle.text = getString(R.string.categories)
        binding.toolbarMenu.ibBack.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        binding.rvCategories.layoutManager = GridLayoutManager(this, 2)
    }

    private fun setupObservers() {
        viewModel.categories.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.shimmerCategories.isVisible = true
                    binding.rvCategories.isVisible = false
                    // Show loading indicator
                }

                is Resource.Success -> {
                    binding.shimmerCategories.isVisible = false
                    binding.rvCategories.isVisible = true
                    resource.data?.let { categories ->
                        categoryAdapter = CategoryAdapter(categories) { category ->
                            // Handle item click
                            val intent = Intent(this, FilterActivity::class.java).apply {
                                putExtra("CATEGORY", category.strCategory)
                            }
                            startActivity(intent)

                        }
                        binding.rvCategories.adapter = categoryAdapter
                    }
                }

                is Resource.Error -> {
                    binding.shimmerCategories.isVisible = false
                    binding.rvCategories.isVisible = false
                    // Show error message
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}
