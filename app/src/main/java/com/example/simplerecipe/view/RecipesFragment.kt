package com.example.simplerecipe.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplerecipe.MainViewModel
import com.example.simplerecipe.adapter.RecipeAdapter
import com.example.simplerecipe.databinding.FragmentRecipesBinding
import com.example.simplerecipe.utility.ConstantValue.Companion.API_KEY
import com.example.simplerecipe.utility.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@FragmentScoped
@ExperimentalCoroutinesApi
class RecipesFragment : Fragment() {
    private lateinit var binding: FragmentRecipesBinding
    private lateinit var mainViewModel: MainViewModel
    private val recipeAdapter by lazy { RecipeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        // Inflate the layout for this fragment
//        fragmentView = inflater.inflate(R.layout.fragment_recipes, container, false)
//        return fragmentView

        binding = FragmentRecipesBinding.inflate(inflater,container,false)
        setupView()
        requestData()
        return binding.root
    }

    private fun setupView() {
        binding.recyclerView.adapter = recipeAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        showShimmerEffect()
    }

    private fun requestData() {
        mainViewModel.getRecipe(applyQuery())
        mainViewModel.recipeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    showShimmerEffect()
                    response.data?.let { recipeAdapter.setData(it) }
                }
                is NetworkResult.Fail -> {
                    showInternetAvalibility()
                    stopShimmerEffect()
                    Toast.makeText(requireContext(),response.message,Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun applyQuery(): Map<String?, String?> {
        val query: HashMap<String, String> = HashMap()
        query.apply {
            this["number"] = "50"
            this["apiKey"] = API_KEY
            this["type"] = "snack"
            this["diet"] = "vegan"
            this["addRecipeInformation"] = "true"
            this["includeNutrition"] = "true"
            this["fillIngredients"] = "true"
        }
        return query.toMap()
    }

    private fun showShimmerEffect() {
        binding.shimmerViewFrame.startShimmer()
        binding.shimmerViewFrame.visibility = View.VISIBLE
    }

    private fun stopShimmerEffect() {
        binding.shimmerViewFrame.stopShimmer()
        binding.shimmerViewFrame.visibility = View.INVISIBLE
    }

    private fun showInternetAvalibility() {
        binding.badInternetImage.visibility = View.VISIBLE
        binding.badInternetText.visibility = View.VISIBLE
    }

    private fun hideInternetAvalibility() {
        binding.badInternetImage.visibility = View.GONE
        binding.badInternetText.visibility = View.GONE
    }


    override fun onResume() {
        super.onResume()
        showShimmerEffect()
    }

    override fun onPause() {
        showShimmerEffect()
        super.onPause()
    }

}