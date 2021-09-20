package com.dorizu.masako.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dorizu.masako.R
import com.dorizu.masako.databinding.ItemIngredientsBinding

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.ListViewHolder>() {

    private var listData = ArrayList<String>()

    fun setData(newListData: List<String>?){
        listData.clear()
        if (!newListData.isNullOrEmpty()) listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemIngredientsBinding.bind(itemView)
        fun bind(data: String){
            with(binding){
                tvIngredients.text = data
                tvNumber.text = (adapterPosition + 1).toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ingredients, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size
}