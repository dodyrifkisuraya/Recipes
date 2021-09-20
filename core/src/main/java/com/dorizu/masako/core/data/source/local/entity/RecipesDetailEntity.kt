package com.dorizu.masako.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipesDetailEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "key")
    val key: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String?,

    @ColumnInfo(name = "times")
    val times: String?,

    @ColumnInfo(name = "portion")
    val portion: String?,

    @ColumnInfo(name = "difficulty")
    val difficulty: String?,

    @ColumnInfo(name = "author")
    val authorName: String?,

    @ColumnInfo(name = "date")
    val datePublished: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "ingredient")
    val ingredient: String,

    @ColumnInfo(name = "step")
    val step: String
)
