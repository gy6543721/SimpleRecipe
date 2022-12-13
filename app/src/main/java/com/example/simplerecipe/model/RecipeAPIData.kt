package com.example.simplerecipe.model


import com.google.gson.annotations.SerializedName

data class RecipeAPIData(
    @SerializedName("number")
    val number: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("totalResults")
    val totalResults: Int
)