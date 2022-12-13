package com.example.simplerecipe.data

import com.example.simplerecipe.model.RecipeAPIData
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val recipeAPIData: RecipeAPI) {
    suspend fun getRecipes(query: Map<String?,String?>): Response<RecipeAPIData> {
        return recipeAPIData.getRecipes(query)
    }
}