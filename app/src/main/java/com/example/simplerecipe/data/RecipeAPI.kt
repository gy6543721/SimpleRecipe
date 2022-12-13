package com.example.simplerecipe.data

import com.example.simplerecipe.model.RecipeAPIData
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RecipeAPI {
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(@QueryMap query: Map<String?,String?>): Response<RecipeAPIData>

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(@QueryMap searchQuery: Map<String, String>): Response<RecipeAPIData>

    @GET("food/jokes/random")
    suspend fun getFoodJoke(@Query("apiKey") apiKey: String): Response<RecipeAPIData>
}