package com.dorizu.masako.saved

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dorizu.masako.R
import com.dorizu.masako.core.databinding.ItemRecipesBinding
import com.dorizu.masako.core.domain.model.RecipesDetail

class RecipesDetailAdapter: RecyclerView.Adapter<RecipesDetailAdapter.ListViewHolder>() {

    private var listData = ArrayList<RecipesDetail>()
    var onItemClick: ((RecipesDetail) -> Unit)? = null

    fun setData(newListData: List<RecipesDetail>?){
        listData.clear()
        if (!newListData.isNullOrEmpty()) listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRecipesBinding.bind(itemView)
        fun bind(data: RecipesDetail){
            with(binding){
                tvTitleRecipes.text = data.title
                tvDifficulty.text = data.difficulty
                (data.times + " · " + data.portion).also { tvDetailRecipes.text = it }
                Glide.with(itemView)
                    .load(data.thumbnail)
                    .centerCrop()
                    .into(imgThumbRecipes)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recipes, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

}