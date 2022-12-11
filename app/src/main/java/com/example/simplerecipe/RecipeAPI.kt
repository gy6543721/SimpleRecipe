package com.example.simplerecipe

import com.example.simplerecipe.data.RecipeAPIData
import retrofit2.Response
import retrofit2.http.*

interface RecipeAPI {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap query:Map <String, String>
    ): Response<RecipeAPIData>
}