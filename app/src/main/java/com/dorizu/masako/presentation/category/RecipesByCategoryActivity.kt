package com.dorizu.masako.presentation.category

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.dorizu.masako.core.data.ResultState
import com.dorizu.masako.core.domain.model.RecipesCategory
import com.dorizu.masako.databinding.ActivityRecipesByCategoryBinding
import com.dorizu.masako.presentation.adapter.CategoryFragmentAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesByCategoryActivity : AppCompatActivity() {

    private val viewModel: RecipesByCategoryViewModel by viewModels()
    private lateinit var binding: ActivityRecipesByCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipesByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(binding.toolbar)

        //get passes category
        val categoryName = intent.extras?.getString(CATEGORY_NAME)

        viewModel.getRecipesCategory()
        viewModel.category.observe(this, {res->
            when(res){
                is ResultState.Success -> {
                    res.data?.let { setupTab(it, categoryName) }
                    with(binding){
                        viewEmptyData.root.visibility = View.GONE
                        viewLoadingData.root.visibility = View.GONE
                    }
                }
                is ResultState.Loading -> {
                    with(binding){
                        viewEmptyData.root.visibility = View.GONE
                        viewLoadingData.root.visibility = View.VISIBLE
                    }
                }
                is ResultState.Error -> {
                    with(binding){
                        viewEmptyData.root.visibility = View.VISIBLE
                        viewEmptyData.tvTitleEmpty.text = res.message
                        viewLoadingData.root.visibility = View.GONE
                    }
                }
                is ResultState.Empty -> {
                    with(binding){
                        viewEmptyData.root.visibility = View.VISIBLE
                        viewLoadingData.root.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setupTab(listCategory: List<RecipesCategory>, selectedTab: String?){
        val adapter = CategoryFragmentAdapter(supportFragmentManager)
        for (category in listCategory) {
            adapter.addFragment(RecipesByCategoryFragment(category.key), category.name)
        }
        with(binding){
            vpCategory.adapter = adapter
            tabCategory.setupWithViewPager(vpCategory)

            vpCategory.currentItem = adapter.getPositionByTitle(selectedTab)
        }
    }

    private fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    companion object{
        const val CATEGORY_NAME = "category_name"
    }
}