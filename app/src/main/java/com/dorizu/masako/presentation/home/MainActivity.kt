package com.dorizu.masako.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dorizu.masako.core.data.ResultState
import com.dorizu.masako.core.ui.ConnectivityLiveData
import com.dorizu.masako.databinding.ActivityMainBinding
import com.dorizu.masako.presentation.adapter.CategoryAdapter
import com.dorizu.masako.presentation.adapter.RecipesAdapter
import com.dorizu.masako.presentation.category.RecipesByCategoryActivity
import com.dorizu.masako.presentation.category.RecipesByCategoryActivity.Companion.CATEGORY_NAME
import com.dorizu.masako.presentation.detail.recipes.DetailRecipesActivity
import com.dorizu.masako.presentation.detail.recipes.DetailRecipesActivity.Companion.RECIPES_KEY
import com.dorizu.masako.presentation.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private var needReload = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryAdapter = CategoryAdapter()
        categoryAdapter.onItemClick = { selectedCategory ->
            val intent = Intent(this, RecipesByCategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME, selectedCategory.name)
            startActivity(intent)
        }

        mainViewModel.category.observe(this, { category ->
            if (category != null) {
                when (category) {
                    is ResultState.Loading -> {
                        binding.shimmerCategory.visibility = View.VISIBLE
                        binding.errorCategory.root.visibility = View.GONE
                    }
                    is ResultState.Success -> {
                        binding.shimmerCategory.visibility = View.INVISIBLE
                        binding.errorCategory.root.visibility = View.GONE
                        categoryAdapter.setData(category.data)
                    }
                    else -> {
                        binding.shimmerCategory.visibility = View.INVISIBLE
                        binding.errorCategory.root.visibility = View.VISIBLE
                        needReload = true
                    }
                }
            }
        })

        val newRecipesAdapter = RecipesAdapter()
        newRecipesAdapter.onItemClick = {selectedRecipes ->
            val intent = Intent(this, DetailRecipesActivity::class.java)
            intent.putExtra(RECIPES_KEY, selectedRecipes.key)
            startActivity(intent)
        }

        mainViewModel.newRecipes.observe(this, {recipes ->
            if (recipes != null){
                when(recipes){
                    is ResultState.Loading -> {
                        binding.shimmerRecipes.visibility = View.VISIBLE
                        binding.errorRecipes.root.visibility = View.GONE
                    }
                    is ResultState.Success -> {
                        binding.shimmerRecipes.visibility = View.GONE
                        binding.errorRecipes.root.visibility = View.GONE
                        newRecipesAdapter.setData(recipes.data)
                    }
                    else -> {
                        binding.shimmerRecipes.visibility = View.GONE
                        binding.errorRecipes.root.visibility = View.VISIBLE
                        needReload = true
                    }
                }
            }
        })

        val connection = ConnectivityLiveData(application)
        connection.observe(this, { avaible ->
            when (avaible) {
                true -> {
                    if (needReload){
                        mainViewModel.getRecipesCategory()
                        mainViewModel.getNewRecipes()
                    }
                    needReload = false
                }
                false ->{
                    needReload = true
                    Toast.makeText(this, "Tidak Ada Koneksi Internet!", Toast.LENGTH_SHORT).show()
                }
            }

        })

        with(binding.rvCategory) {
            layoutManager = GridLayoutManager(this@MainActivity, 2, GridLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        with(binding.rvNewRecipes){
            layoutManager = GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)
            adapter = newRecipesAdapter
        }

        binding.etSearchRecipes.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.rlSaved.setOnClickListener {
            val uri = Uri.parse("masako://saved")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        binding.errorCategory.btnReload.setOnClickListener {
            mainViewModel.getRecipesCategory()
        }
        binding.errorRecipes.btnReload.setOnClickListener {
            mainViewModel.getNewRecipes()
        }
    }
}