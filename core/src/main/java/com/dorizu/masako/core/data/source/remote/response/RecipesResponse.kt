package com.dorizu.masako.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RecipesResponse(
    @SerializedName("title")
    val title: String,

    @SerializedName("thumb")
    val thumbnail: String,

    @SerializedName("key")
    val key: String,

    @SerializedName("times")
    val times: String,

    @SerializedName("portion")
    val portion: String,

    @SerializedName("dificulty")
    val difficulty: String,

    @SerializedName("difficulty")
    val difficultyInSearch: String,

    @SerializedName("serving")
    val serving: String
)