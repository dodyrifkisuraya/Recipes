package com.dorizu.masako.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResultRecipesCategoryResponse(
    @field:SerializedName("status")
    val status: Boolean,

    @field:SerializedName("results")
    val results: List<RecipesCategoryResponse>
)
