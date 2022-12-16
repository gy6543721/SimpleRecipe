package com.example.simplerecipe.data

import android.util.Log
import com.example.simplerecipe.model.RecipeAPIData
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {

    // This is not initialized
    lateinit var recipeAPIData: RecipeAPI

    suspend fun getRecipes(query: Map<String?, String?>): Response<RecipeAPIData> {
        Log.d("TAG", query.toString())
        return recipeAPIData.getRecipes(query)
    }
}