package com.dorizu.masako.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResultRecipesResponse(
    @SerializedName("results")
    val results: List<RecipesResponse>
)