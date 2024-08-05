package com.example.masakapa.ui.view.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.masakapa.R
import com.example.masakapa.databinding.ActivityMainBinding
import com.example.masakapa.ui.view.category.CategoryActivity
import com.example.masakapa.ui.view.detail.DetailMealActivity
import com.example.masakapa.ui.view.filter.FilterActivity
import com.example.masakapa.ui.viewmodel.MealViewModel
import com.example.masakapa.ui.viewmodel.MealViewModelFactory
import com.example.masakapa.utils.Resource

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MealViewModel by viewModels { MealViewModelFactory(this) }
    private lateinit var adapter: MealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setupRecyclerView()
        setupObservers()
        setupSearch()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    private fun initView() {
        binding.toolbarHome.collapseToolbar.setExpandedTitleColor(Color.argb(0, 0, 0, 0))
        binding.toolbarHome.collapseToolbar.setContentScrimColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )

        binding.tvCategory.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        adapter = MealAdapter { meal ->
            val intent = Intent(this, DetailMealActivity::class.java).apply {
                putExtra("MEAL_ID", meal.idMeal)
            }
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.meals.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show shimmer animation
                    binding.shimmerCategories.isVisible = true
                    binding.recyclerView.isVisible = false
                }

                is Resource.Success -> {
                    // Hide shimmer animation and show data
                    binding.shimmerCategories.isVisible = false
                    binding.recyclerView.isVisible = true
                    resource.data?.let { adapter.submitList(it) }
                }

                is Resource.Error -> {
                    binding.shimmerCategories.isVisible = false
                    binding.recyclerView.isVisible = false
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupSearch() {
        binding.toolbarHome.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val intent = Intent(this@MainActivity, FilterActivity::class.java).apply {
                        putExtra("SEARCH_QUERY", query)
                    }
                    startActivity(intent)

                    // Clear focus from SearchView
                    binding.toolbarHome.searchView.clearFocus()

                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text changes if needed
                return true
            }
        })
    }
}