package com.example.simplerecipe.adapter

import android.graphics.Color.green
import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.simplerecipe.R

class RecipeCardRecyclerviewBindingAdapter {
    companion object {

        @BindingAdapter("setImage")
        @JvmStatic
        fun setImage(imageView: ImageView, imageURL: String) {
            // Use Coil to load imageURL
            imageView.load(imageURL) {
                crossfade(600)
            }
        }


        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfTime")
        @JvmStatic
        fun setNumberOfTime(textView: TextView, time: Int) {
            textView.text = time.toString()
        }

        @BindingAdapter("setVeganStatus")
        @JvmStatic
        fun setVeganStatus(view: View, vegan: Boolean) {
            if (vegan) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(view.context, R.color.green)
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(view.context, R.color.green)
                        )
                    }
                }
            }
        }
    }
}