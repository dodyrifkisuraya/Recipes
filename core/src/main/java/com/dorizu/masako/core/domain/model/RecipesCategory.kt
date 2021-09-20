package com.dorizu.masako.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipesCategory(
    val name: String,
    val website: String,
    val key: String
): Parcelable