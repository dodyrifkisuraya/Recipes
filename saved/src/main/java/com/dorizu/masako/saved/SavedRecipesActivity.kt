package com.dorizu.masako.saved

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.dorizu.masako.core.data.ResultState
import com.dorizu.masako.di.DynamicFeatureDependencies
import com.dorizu.masako.presentation.detail.recipes.DetailRecipesActivity
import com.dorizu.masako.saved.databinding.ActivitySavedRecipesBinding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class SavedRecipesActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: ActivitySavedRecipesBinding
    private val viewModel: SavedRecipesViewModel by viewModels{ factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerSavedComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    DynamicFeatureDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivitySavedRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(binding.toolbar)

        val recipesAdapter = RecipesDetailAdapter()
        recipesAdapter.onItemClick = {selectedRecipes ->
            val intent = Intent(this, DetailRecipesActivity::class.java)
            intent.putExtra(DetailRecipesActivity.RECIPES_DETAIL, selectedRecipes)
            startActivity(intent)
        }

        viewModel.recipesSaved.observe(this, {res ->
            when(res){
                is ResultState.Success -> {
                    recipesAdapter.setData(res.data)
                    with(binding){
                        viewEmptyData.root.visibility = View.GONE
                        viewLoadingData.root.visibility = View.GONE
                    }
                }
                is ResultState.Loading -> {
                    with(binding){
                        viewLoadingData.root.visibility = View.VISIBLE
                        viewEmptyData.root.visibility = View.GONE
                    }
                }
                is ResultState.Error -> {
                    with(binding){
                        viewEmptyData.tvTitleEmpty.text = res.message
                        viewEmptyData.root.visibility = View.VISIBLE
                        viewLoadingData.root.visibility = View.GONE
                    }
                }
                is ResultState.Empty -> {
                    recipesAdapter.setData(null)
                    with(binding){
                        viewLoadingData.root.visibility = View.GONE
                        viewEmptyData.tvTitleEmpty.text = getString(R.string.title_empty_saved)
                        viewEmptyData.root.visibility = View.VISIBLE
                    }
                }
            }
        })

        with(binding.rvRecipeSaved){
            adapter = recipesAdapter
        }
    }

    private fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.getSavedRecipes()
    }
}