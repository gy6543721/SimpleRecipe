package com.example.simplerecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simplerecipe.model.Result
import com.example.simplerecipe.databinding.RecipeCardRecyclerviewBinding
import com.example.simplerecipe.model.RecipeAPIData
import com.example.simplerecipe.utility.DataDifferenceUtility

class RecipeAdapter: RecyclerView.Adapter<RecipeAdapter.MyViewHolder>() {
    private var recipeList = emptyList<Result>()

    class MyViewHolder(private var binding: RecipeCardRecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.resultDataBinding = result
            binding.executePendingBindings()
        }
        companion object {
            fun from(viewGroup: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val binding = RecipeCardRecyclerviewBinding.inflate(layoutInflater, viewGroup, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }


    fun setData(data: RecipeAPIData) {
        val recipeDifference = DataDifferenceUtility(recipeList, data.results)
        val dataDifferenceResult = DiffUtil.calculateDiff(recipeDifference)
        recipeList = data.results
        dataDifferenceResult.dispatchUpdatesTo(this)

//        // Don't use notifyDataSetChanged() because it will update the whole list
//        notifyDataSetChanged()
    }
}