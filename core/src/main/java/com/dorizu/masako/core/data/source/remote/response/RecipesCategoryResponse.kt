package com.dorizu.masako.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RecipesCategoryResponse(
    @field:SerializedName("category")
    val name: String,

    @field:SerializedName("url")
    val urlWebsite: String,

    @field:SerializedName("key")
    val key: String
)