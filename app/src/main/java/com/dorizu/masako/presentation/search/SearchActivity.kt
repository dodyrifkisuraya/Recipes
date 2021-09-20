package com.dorizu.masako.presentation.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dorizu.masako.core.data.ResultState
import com.dorizu.masako.databinding.ActivitySearchBinding
import com.dorizu.masako.presentation.adapter.RecipesAdapter
import com.dorizu.masako.presentation.detail.recipes.DetailRecipesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(binding.searchBar.searchLayout)
        setupSearchBar()

        val recipesAdapter = RecipesAdapter()
        recipesAdapter.onItemClick = {selectedRecipes ->
            val intent = Intent(this, DetailRecipesActivity::class.java)
            intent.putExtra(DetailRecipesActivity.RECIPES_KEY, selectedRecipes.key)
            startActivity(intent)
        }

        viewModel.recipes.observe(this, {res ->
            when(res){
                is ResultState.Success -> {
                    recipesAdapter.setData(res.data)
                    binding.viewLoadingData.root.visibility = View.GONE
                }
                is ResultState.Loading -> {
                    binding.viewLoadingData.root.visibility = View.VISIBLE
                }
                is ResultState.Error -> {
                    binding.viewLoadingData.root.visibility = View.GONE
                    Toast.makeText(this, res.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is ResultState.Empty -> {
                    binding.viewLoadingData.root.visibility = View.GONE
                    recipesAdapter.setData(null)
                    Toast.makeText(this, "Data Tidak Tersedia!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        with(binding.rvSearchRecipes){
            layoutManager = StaggeredGridLayoutManager (2, GridLayoutManager.VERTICAL)
            adapter = recipesAdapter
        }
    }

    private val editorListener =
        OnEditorActionListener { v, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    binding.viewLoadingData.root.visibility = View.VISIBLE
                    viewModel.getResultSearch(v.text.toString())
                }
            }
            false
        }

    private fun setupSearchBar(){
        binding.searchBar.etSearchRecipes.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                with(binding.searchBar){
                    ibClear.visibility = View.VISIBLE
                    ibClear.setOnClickListener {
                        etSearchRecipes.text.clear()
                    }
                    if (etSearchRecipes.text.isEmpty()) ibClear.visibility = View.GONE
                }
            }

            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
            }
        })

        binding.searchBar.etSearchRecipes.setOnEditorActionListener(editorListener)
    }

    private fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}