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
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipesFragment : Fragment() {

//    private lateinit var fragmentView: View
//    private var shimmerViewContainer: ShimmerFrameLayout? = null
    private lateinit var binding: FragmentRecipesBinding
    private lateinit var mainViewModel: MainViewModel
    private val recipeAdapter by lazy { RecipeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        // Inflate the layout for this fragment
//        fragmentView = inflater.inflate(R.layout.fragment_recipes, container, false)
//        return fragmentView
        binding = FragmentRecipesBinding.inflate(inflater,container,false)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupView()
        requestData()
        return binding.root
    }

    private fun setupView() {
        binding.recyclerView.adapter = recipeAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun requestData() {
        mainViewModel.getRecipe(applyQuery())
        mainViewModel.recipeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { recipeAdapter.setData(it) }
                }
                is NetworkResult.Fail -> {
                    hideShimmerEffect()
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
        binding.shimmerViewContainer.startShimmer()
    }

    private fun hideShimmerEffect() {
        binding.shimmerViewContainer.stopShimmer()
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