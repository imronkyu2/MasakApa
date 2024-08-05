package com.example.masakapa.data.repo

import android.content.Context
import com.example.masakapa.data.remot.RetrofitInstance

class MealRepository(context: Context) {
    private val api = RetrofitInstance.create(context)

    suspend fun getMeals() = api.getSearchMeals()


    suspend fun getMealDetail(mealId: String) = api.getMealDetail(mealId)


    suspend fun getCategories() = api.getCategories()

    // Added for searching meals by name
    suspend fun searchMeals(query: String) = api.searchMeals(query)

    // Added for fetching meals by category
    suspend fun getMealsByCategory(category: String) = api.getMealsByCategory(category)

}