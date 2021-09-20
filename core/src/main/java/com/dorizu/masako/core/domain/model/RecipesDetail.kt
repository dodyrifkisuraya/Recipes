package com.dorizu.masako.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipesDetail(
    val title: String,
    val thumbnail: String?,
    val key: String,
    val times: String?,
    val portion: String?,
    val difficulty: String?,
    val authorName: String?,
    val datePublished: String?,
    val description: String?,
    val ingredient: List<String>,
    val step: List<String>
): Parcelable
