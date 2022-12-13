package com.example.simplerecipe.utility

import androidx.recyclerview.widget.DiffUtil
import com.example.simplerecipe.model.*

class DataDifferenceUtility(
    private val oldList: List<Result>,
    private val newList: List<Result>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // compare the address of oldList and newList
        return oldList[newItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // compare the value of oldList and newList
        return oldList[newItemPosition] == newList[newItemPosition]
    }
}