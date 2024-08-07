package com.example.masakapa.ui.view.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.masakapa.R
import com.example.masakapa.databinding.ActivityDetailMealBinding
import com.example.masakapa.ui.viewmodel.MealViewModel
import com.example.masakapa.ui.viewmodel.MealViewModelFactory
import com.example.masakapa.utils.Ingredients
import com.example.masakapa.utils.Instructions
import com.example.masakapa.utils.Resource

class DetailMealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMealBinding
    private val viewModel: MealViewModel by viewModels { MealViewModelFactory(this) }
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detailMealActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve meal data from intent
        val mealId: String? = intent.getStringExtra("MEAL_ID")
        mealId?.let {
            viewModel.fetchMealDetail(it)
        }
        setToolbarAndButton()
        setupObservers()
    }

    private fun setToolbarAndButton() {
        // Handle color toolbar when scrolling up
        binding.collapseToolbar.setExpandedTitleColor(Color.argb(0, 0, 0, 0))
        binding.collapseToolbar.setContentScrimColor(ContextCompat.getColor(this, R.color.white))
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)

        binding.toolbar.setOnClickListener {
            finish()
        }

        binding.llYoutube.setOnClickListener {
            val intentYoutube = Intent(Intent.ACTION_VIEW)
            intentYoutube.data = Uri.parse(youtubeLink)
            startActivity(intentYoutube)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        viewModel.mealDetail.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show loading indicator
                }

                is Resource.Success -> {
                    // Bind data to UI
                    resource.data?.let { meal ->
                        binding.tvMeal.text = meal.strMeal

                        Glide.with(this)
                            .load(meal.strMealThumb)
                            .placeholder(R.drawable.placeholder)
                            .into(binding.ivBgMeal)

                        Glide.with(this)
                            .load(meal.strMealThumb)
                            .circleCrop()
                            .placeholder(R.drawable.placeholder)
                            .into(binding.ivMeal)

                        binding.tvSubMeal.text = "${meal.strArea} | ${meal.strCategory}"
                        youtubeLink = meal.strYoutube
                        binding.tvIngredients.text = Ingredients.getFormattedIngredients(meal)
                        binding.tvMeasures.text = Ingredients.getFormattedMeasures(meal)
                        binding.jtvInstructions.text = Instructions.getFormattedInstructions(meal.strInstructions)

                    }
                }

                is Resource.Error ->
                    // Show error message
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
            }
        }
    }


}
