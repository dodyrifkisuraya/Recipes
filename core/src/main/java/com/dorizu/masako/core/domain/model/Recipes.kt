package com.dorizu.masako.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipes(
    val title: String?,
    val thumbnail: String?,
    val key: String?,
    val times: String?,
    val portion: String?,
    val difficulty: String?,
    val difficultyInSearch: String?,
    val serving: String?
): Parcelable