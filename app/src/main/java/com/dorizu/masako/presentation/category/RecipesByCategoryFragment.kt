package com.dorizu.masako.presentation.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dorizu.masako.core.data.ResultState
import com.dorizu.masako.databinding.FragmentRecipesByCategoryBinding
import com.dorizu.masako.presentation.adapter.RecipesAdapter
import com.dorizu.masako.presentation.detail.recipes.DetailRecipesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesByCategoryFragment(private val key: String) : Fragment() {

    private var _binding: FragmentRecipesByCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecipesByCategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesByCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipesAdapter = RecipesAdapter()
        recipesAdapter.onItemClick = {selectedRecipes ->
            val intent = Intent(requireContext(), DetailRecipesActivity::class.java)
            intent.putExtra(DetailRecipesActivity.RECIPES_KEY, selectedRecipes.key)
            startActivity(intent)
        }

        viewModel.getRecipesByCategory(key)
        viewModel.recipes.observe(viewLifecycleOwner, {res->
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
                    Toast.makeText(requireContext(), res.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })

        with(binding.rvRecipeByCategory){
            adapter = recipesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}