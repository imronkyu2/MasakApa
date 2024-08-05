package com.example.masakapa.data.remot

import com.example.masakapa.data.model.CategoryResponse
import com.example.masakapa.data.model.MealsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.php?f=b")
    suspend fun getSearchMeals(): Response<MealsResponse>

    @GET("lookup.php")
    suspend fun getMealDetail(@Query("i") mealId: String): Response<MealsResponse>

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): Response<MealsResponse>

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Response<MealsResponse>


}
