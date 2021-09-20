package com.dorizu.masako.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RecipesDetailResponse(
    @SerializedName("title")
    val title: String,

    @SerializedName("thumb")
    val thumbnail: String,

    @SerializedName("times")
    val times: String,

    @SerializedName("servings")
    val portion: String,

    @SerializedName("dificulty")
    val difficulty: String,

    @SerializedName("author")
    val author: AuthorResponse,

    @SerializedName("desc")
    val description: String,

    @SerializedName("ingredient")
    val ingredient: List<String>,

    @SerializedName("step")
    val step: List<String>
)

data class AuthorResponse(
    @SerializedName("user")
    val user: String,

    @SerializedName("datePublished")
    val datePublished: String
)
