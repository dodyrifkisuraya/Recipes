package com.dorizu.masako.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dorizu.masako.R
import com.dorizu.masako.core.domain.model.RecipesCategory
import com.dorizu.masako.databinding.ItemCategoryBinding

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.ListViewHolder>() {

    private var listData = ArrayList<RecipesCategory>()
    var onItemClick: ((RecipesCategory) -> Unit)? = null

    fun setData(newListData: List<RecipesCategory>?){
        listData.clear()
        if (!newListData.isNullOrEmpty()) listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCategoryBinding.bind(itemView)
        fun bind(data: RecipesCategory){
            with(binding){
                tvCategoryName.text = data.name
                iconCategory.setImageResource(
                    when(data.key){
                        "dessert" -> R.drawable.ic_dessert
                        "sarapan" -> R.drawable.ic_breakfast
                        "resep-ayam" -> R.drawable.ic_chicken
                        "makan-malam" -> R.drawable.ic_dinner
                        "masakan-hari-raya" -> R.drawable.ic_eid
                        "makan-siang" -> R.drawable.ic_lunch
                        "resep-daging" -> R.drawable.ic_meat
                        "resep-seafood" -> R.drawable.ic_seafood
                        "masakan-tradisional" -> R.drawable.ic_traditional
                        "resep-sayuran" -> R.drawable.ic_vegetable
                        else -> R.drawable.ic_breakfast
                    }
                )
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

}