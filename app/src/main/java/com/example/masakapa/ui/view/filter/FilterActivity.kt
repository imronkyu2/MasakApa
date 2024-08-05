package com.example.masakapa.ui.view.filter

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.masakapa.R
import com.example.masakapa.databinding.ActivityFilterBinding
import com.example.masakapa.ui.view.detail.DetailMealActivity
import com.example.masakapa.ui.view.home.MealAdapter
import com.example.masakapa.ui.viewmodel.MealViewModel
import com.example.masakapa.ui.viewmodel.MealViewModelFactory
import com.example.masakapa.utils.Resource

class FilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding
    private val viewModel: MealViewModel by viewModels { MealViewModelFactory(this) }
    private lateinit var mealAdapter: MealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Get category or search query from intent
        val category = intent.getStringExtra("CATEGORY")
        val searchQuery = intent.getStringExtra("SEARCH_QUERY")

        initListener()
        setupRecyclerView()
        setupObservers()

        // Fetch meals based on category or search query
        if (category != null) {
            viewModel.fetchMealsByCategory(category)
            binding.toolbarMenu.tvTitle.text = getString(R.string.filterByCategory)
        } else if (searchQuery != null) {
            viewModel.searchMealsByName(searchQuery)
            binding.toolbarMenu.tvTitle.text = getString(R.string.filterByName)
        } else {
            Toast.makeText(this, "Data Not Found", Toast.LENGTH_LONG).show()
            finish()
        }


        ViewCompat.setOnApplyWindowInsetsListener(binding.filterByCategoryActivity) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initListener() {
        binding.toolbarMenu.ibBack.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        binding.rvMeals.layoutManager = GridLayoutManager(this, 2)
        mealAdapter = MealAdapter { meal ->
            val intent = Intent(this, DetailMealActivity::class.java).apply {
                putExtra("MEAL_ID", meal.idMeal)
            }
            startActivity(intent)
        }
        binding.rvMeals.adapter = mealAdapter
    }

    private fun setupObservers() {
        viewModel.filteredMeals.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show loading indicator
                    binding.shimmerCategories.isVisible = true
                    binding.rvMeals.isVisible = false
                }

                is Resource.Success -> {
                    binding.shimmerCategories.isVisible = false
                    binding.rvMeals.isVisible = true
                    resource.data?.let { meals ->
                        mealAdapter.submitList(meals)
                    }
                }

                is Resource.Error -> {
                    // Show error message
                    binding.shimmerCategories.isVisible = false
                    binding.rvMeals.isVisible = false
//                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    resource.message?.let { showCustomToast(it, 1200L) }
                    finish()
                }
            }
        }
    }

    private fun showCustomToast(message: String, durationInMillis: Long) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)

        // Show the toast
        toast.show()

        // Cancel the toast after the specified duration
        Handler(Looper.getMainLooper()).postDelayed({
            toast.cancel()
        }, durationInMillis)
    }
}