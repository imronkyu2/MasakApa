package com.example.masakapa.utils

import com.example.masakapa.data.model.Meal

class Ingredients {
    companion object {
        fun getFormattedIngredients(meal: Meal): String {
            val ingredients = listOfNotNull(
                meal.strIngredient1.takeIf { !it.isNullOrBlank() },
                meal.strIngredient2.takeIf { !it.isNullOrBlank() },
                meal.strIngredient3.takeIf { !it.isNullOrBlank() },
                meal.strIngredient4.takeIf { !it.isNullOrBlank() },
                meal.strIngredient5.takeIf { !it.isNullOrBlank() },
                meal.strIngredient6.takeIf { !it.isNullOrBlank() },
                meal.strIngredient7.takeIf { !it.isNullOrBlank() },
                meal.strIngredient8.takeIf { !it.isNullOrBlank() },
                meal.strIngredient9.takeIf { !it.isNullOrBlank() },
                meal.strIngredient10.takeIf { !it.isNullOrBlank() },
                meal.strIngredient11.takeIf { !it.isNullOrBlank() },
                meal.strIngredient12.takeIf { !it.isNullOrBlank() },
                meal.strIngredient13.takeIf { !it.isNullOrBlank() },
                meal.strIngredient14.takeIf { !it.isNullOrBlank() },
                meal.strIngredient15.takeIf { !it.isNullOrBlank() },
                meal.strIngredient16.takeIf { !it.isNullOrBlank() },
                meal.strIngredient17.takeIf { !it.isNullOrBlank() },
                meal.strIngredient18.takeIf { !it.isNullOrBlank() },
                meal.strIngredient19.takeIf { !it.isNullOrBlank() },
                meal.strIngredient20.takeIf { !it.isNullOrBlank() }
            )
            return ingredients.joinToString("\n") { "• $it" }
        }

        fun getFormattedMeasures(meal: Meal): String {
            val measures = listOfNotNull(
                meal.strMeasure1.takeIf { !it.isNullOrBlank() },
                meal.strMeasure2.takeIf { !it.isNullOrBlank() },
                meal.strMeasure3.takeIf { !it.isNullOrBlank() },
                meal.strMeasure4.takeIf { !it.isNullOrBlank() },
                meal.strMeasure5.takeIf { !it.isNullOrBlank() },
                meal.strMeasure6.takeIf { !it.isNullOrBlank() },
                meal.strMeasure7.takeIf { !it.isNullOrBlank() },
                meal.strMeasure8.takeIf { !it.isNullOrBlank() },
                meal.strMeasure9.takeIf { !it.isNullOrBlank() },
                meal.strMeasure10.takeIf { !it.isNullOrBlank() },
                meal.strMeasure11.takeIf { !it.isNullOrBlank() },
                meal.strMeasure12.takeIf { !it.isNullOrBlank() },
                meal.strMeasure13.takeIf { !it.isNullOrBlank() },
                meal.strMeasure14.takeIf { !it.isNullOrBlank() },
                meal.strMeasure15.takeIf { !it.isNullOrBlank() },
                meal.strMeasure16.takeIf { !it.isNullOrBlank() },
                meal.strMeasure17.takeIf { !it.isNullOrBlank() },
                meal.strMeasure18.takeIf { !it.isNullOrBlank() },
                meal.strMeasure19.takeIf { !it.isNullOrBlank() },
                meal.strMeasure20.takeIf { !it.isNullOrBlank() }
            )
            return measures.joinToString("\n") { it }
        }
    }
}