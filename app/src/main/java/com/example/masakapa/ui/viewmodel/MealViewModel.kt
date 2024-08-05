package com.example.masakapa.ui.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masakapa.data.model.Category
import com.example.masakapa.data.model.Meal
import com.example.masakapa.data.repo.MealRepository
import com.example.masakapa.utils.Resource
import kotlinx.coroutines.launch

class MealViewModel(private val repository: MealRepository) : ViewModel() {
    val meals: MutableLiveData<Resource<List<Meal>>> = MutableLiveData()
    val mealDetail: MutableLiveData<Resource<Meal>> = MutableLiveData()
    val categories: MutableLiveData<Resource<List<Category>>> = MutableLiveData()
    val filteredMeals: MutableLiveData<Resource<List<Meal>>> = MutableLiveData()

    init {
        fetchMeals()
        fetchCategories()
    }

    private fun fetchMeals() {
        viewModelScope.launch {
            meals.postValue(Resource.Loading())
            try {
                val response = repository.getMeals()//get meal
                if (response.isSuccessful) {
                    response.body()?.meals?.let {
                        meals.postValue(Resource.Success(it))
                    }
                } else {
                    meals.postValue(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                meals.postValue(Resource.Error(e.localizedMessage ?: "An error occurred"))
            }
        }
    }

    fun fetchMealDetail(mealId: String) {
        viewModelScope.launch {
            mealDetail.postValue(Resource.Loading())
            try {
                val response = repository.getMealDetail(mealId)
                if (response.isSuccessful) {
                    response.body()?.meals?.firstOrNull()?.let {
                        mealDetail.postValue(Resource.Success(it))
                    } ?: mealDetail.postValue(Resource.Error("Meal not found"))
                } else {
                    mealDetail.postValue(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                mealDetail.postValue(Resource.Error(e.localizedMessage ?: "An error occurred"))
            }
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            categories.postValue(Resource.Loading())
            try {
                val response = repository.getCategories()
                if (response.isSuccessful) {
                    response.body()?.categories?.let {
                        categories.postValue(Resource.Success(it))
                    }
                } else {
                    categories.postValue(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                categories.postValue(Resource.Error(e.localizedMessage ?: "An error occurred"))
            }
        }
    }


    fun searchMealsByName(query: String) {
        viewModelScope.launch {
            filteredMeals.postValue(Resource.Loading())
            try {
                val response = repository.searchMeals(query)
                if (response.isSuccessful) {
                    response.body()?.meals?.let {
                        filteredMeals.postValue(Resource.Success(it))
                    } ?: filteredMeals.postValue(Resource.Error("No meals found"))
                } else {
                    filteredMeals.postValue(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                filteredMeals.postValue(Resource.Error(e.localizedMessage ?: "An error occurred"))
            }
        }
    }

    fun fetchMealsByCategory(category: String) {
        viewModelScope.launch {
            filteredMeals.postValue(Resource.Loading())
            try {
                val response = repository.getMealsByCategory(category)
                if (response.isSuccessful) {
                    response.body()?.meals?.let {
                        filteredMeals.postValue(Resource.Success(it))
                    } ?: filteredMeals.postValue(Resource.Error("No meals found"))
                } else {
                    filteredMeals.postValue(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                filteredMeals.postValue(Resource.Error(e.localizedMessage ?: "An error occurred"))
            }
        }
    }
}