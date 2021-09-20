package com.dorizu.masako.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResultRecipesDetailResponse(
    @SerializedName("results")
    val results: RecipesDetailResponse
)
