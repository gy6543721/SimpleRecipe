package com.example.simplerecipe.data

import com.example.simplerecipe.model.RecipeAPIData
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class RemoteDataSource @Inject constructor(private val recipeAPIData: RecipeAPI) {
    suspend fun getRecipes(query: Map<String?,String?>): Response<RecipeAPIData> {
        return recipeAPIData.getRecipes(query)
    }
}