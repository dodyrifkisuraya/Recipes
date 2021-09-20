package com.dorizu.masako.presentation.detail.recipes

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.dorizu.masako.R
import com.dorizu.masako.core.data.ResultState
import com.dorizu.masako.core.domain.model.RecipesDetail
import com.dorizu.masako.databinding.ActivityDetailRecipesBinding
import com.dorizu.masako.presentation.adapter.IngredientsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRecipesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRecipesBinding
    private val viewModel: DetailRecipesViewModel by viewModels()
    private var isSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setExpanded()

        //get recipes detail from local
        val recipesDetail = intent.getParcelableExtra<RecipesDetail>(RECIPES_DETAIL)
        recipesDetail?.let {
            showDetailRecipes(it)
            viewModel.checkIsSavedRecipes(it.key)
        }

        //get passes RECIPES_KEY
        val recipeKey = intent.extras?.getString(RECIPES_KEY)
        recipeKey?.let {
            viewModel.getRecipesDetail(it)
            viewModel.checkIsSavedRecipes(it)
        }

        viewModel.recipesDetail.observe(this, {res ->
            when(res){
                is ResultState.Success -> {
                    showDetailRecipes(res.data)
                }
                is ResultState.Loading -> {
                    with(binding.content){
                        groupDetailRecipes.visibility = View.GONE
                    }
                    with(binding){
                        viewLoadingData.root.visibility = View.VISIBLE
                        viewEmptyData.root.visibility = View.GONE
                    }
                }
                is ResultState.Error -> {
                    with(binding){
                        viewLoadingData.root.visibility = View.GONE
                        viewEmptyData.root.visibility = View.VISIBLE
                        viewEmptyData.tvTitleEmpty.text = res.message
                    }
                }
                is ResultState.Empty -> {
                    with(binding){
                        viewLoadingData.root.visibility = View.GONE
                        viewEmptyData.root.visibility = View.VISIBLE
                    }
                }
            }
        })

        viewModel.isSaved.observe(this, {res ->
            setStatusFavorite(res)
        })
    }

    private fun showDetailRecipes(recipes: RecipesDetail?){

        recipes?.let {
            Glide.with(this@DetailRecipesActivity)
                .load(it.thumbnail)
                .placeholder(R.color.grey_light)
                .into(binding.ivThumbnail)
            binding.fabFavorite.setOnClickListener {
                if (!isSaved){
                    viewModel.saveRecipes(recipes)
                }else{
                    viewModel.deleteRecipes(recipes.key)
                }
                setStatusFavorite(!isSaved)
            }

            with(binding.content){
                tvNameRecipes.text = it.title
                tvLevelDifficulty.text = it.difficulty ?: "-"
                tvTimeCook.text = it.times ?: "-"
                tvPortion.text = it.portion ?: "-"
                tvDescription.text = it.description

                val ingredientsAdapter = IngredientsAdapter()
                ingredientsAdapter.setData(it.ingredient)
                with(rvIngredients){
                    layoutManager = LinearLayoutManager(this@DetailRecipesActivity)
                    adapter = ingredientsAdapter
                }

                val stepAdapter = IngredientsAdapter()
                stepAdapter.setData(it.step)
                with(rvStep){
                    layoutManager = LinearLayoutManager(this@DetailRecipesActivity)
                    adapter = stepAdapter
                }
            }
        }

        with(binding){
            viewLoadingData.root.visibility = View.GONE
            viewEmptyData.root.visibility = View.GONE
            appBar.visibility = View.VISIBLE
            fabFavorite.visibility = View.VISIBLE
            content.groupDetailRecipes.visibility = View.VISIBLE
        }
    }

    private fun setStatusFavorite(status: Boolean){
        if (!status){
            isSaved = false
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_bookmark_outline
                )
            )
        }else{
            isSaved = true
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_bookmark_fill
                )
            )
        }
    }

    private fun setExpanded(){
        with(binding.content){
            rlDescription.setExtendedView(btnExpandDescription, tvDescription, clContentDetail)
            rlIngredient.setExtendedView(btnExpandIngredients, rvIngredients, clContentDetail)
            rlStep.setExtendedView(btnExpandStep, rvStep, clContentDetail)
        }
    }

    private fun View.setExtendedView(iconExpand: Button, hiddenObject: View, viewGroup: ViewGroup){
        this.setOnClickListener {
            when(hiddenObject.visibility){
                View.VISIBLE -> {
                    TransitionManager.beginDelayedTransition(viewGroup, AutoTransition())
                    hiddenObject.visibility = View.GONE
                    iconExpand.setBackgroundResource(R.drawable.ic_down_24)
                }
                View.GONE -> {
                    TransitionManager.beginDelayedTransition(viewGroup, AutoTransition())
                    hiddenObject.visibility = View.VISIBLE
                    iconExpand.setBackgroundResource(R.drawable.ic_up_24)
                }
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back_rounded_background)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    companion object{
        const val RECIPES_KEY = "recipes_key"
        const val RECIPES_DETAIL = "recipes_detail"
    }
}