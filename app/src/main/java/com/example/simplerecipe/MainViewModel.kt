package com.example.simplerecipe

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplerecipe.data.Repository
import com.example.simplerecipe.model.RecipeAPIData
import com.example.simplerecipe.utility.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {

    var recipeResponse: MutableLiveData<NetworkResult<RecipeAPIData>> = MutableLiveData()

    fun getRecipe(query: Map<String?, String?>) = viewModelScope.launch {
        getRecipeSafeCall(query)
    }

    private suspend fun getRecipeSafeCall(queryMap: Map<String?, String?>) {

        recipeResponse.value = NetworkResult.Loading()

//        Log.d("TAG", repository.remoteData.getRecipes(queryMap).toString())

        if(hasInternetConnection()) {
            try {
                val response = repository.remoteData.getRecipes(queryMap)
                recipeResponse.value = handleRecipeResponse(response)
            } catch (_: Exception) {
                recipeResponse.value = NetworkResult.Fail(null,"Recipe Not Found")
            }
        } else {
            recipeResponse.value = NetworkResult.Fail(null,"No Internet Connection")
        }
    }

    private fun handleRecipeResponse(response: Response<RecipeAPIData>): NetworkResult<RecipeAPIData>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Fail(null,"TimeOut")
            }
            response.code() == 402 -> {
                return NetworkResult.Fail(null,"API Key Limited")
            }
            response.body()?.results.isNullOrEmpty() -> {
                return NetworkResult.Fail(null,"Recipe Not Found")
            }
            response.isSuccessful -> {
                val recipeResponse = response.body()
                return NetworkResult.Success(recipeResponse!!)
            }
            else -> {
                return NetworkResult.Fail(null,response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}