package com.dorizu.masako.core.utils

import com.dorizu.masako.core.data.source.local.entity.RecipesDetailEntity
import com.dorizu.masako.core.data.source.remote.response.RecipesCategoryResponse
import com.dorizu.masako.core.data.source.remote.response.RecipesDetailResponse
import com.dorizu.masako.core.data.source.remote.response.RecipesResponse
import com.dorizu.masako.core.domain.model.Recipes
import com.dorizu.masako.core.domain.model.RecipesCategory
import com.dorizu.masako.core.domain.model.RecipesDetail
import com.dorizu.masako.core.utils.Converters.jsonToList
import com.dorizu.masako.core.utils.Converters.listToJson

object DataMapper {

    fun mapListRecipeCategoryResponseToDomain(input: List<RecipesCategoryResponse>) =
        input.map {
            RecipesCategory(
                name = it.name,
                website = it.urlWebsite,
                key = it.key
            )
        }

    fun mapListRecipesResponseToDomain(input: List<RecipesResponse>) =
        input.map {
            Recipes(
                title = it.title,
                thumbnail = it.thumbnail,
                key = it.key,
                portion = it.portion,
                times =  it.times,
                difficulty = it.difficulty,
                difficultyInSearch = it.difficultyInSearch,
                serving = it.serving
            )
        }

    fun mapRecipesDetailResponseToDomain(input: RecipesDetailResponse, key: String) =
        RecipesDetail(
            title = input.title,
            thumbnail = input.thumbnail,
            key = key,
            times = input.times,
            portion = input.portion,
            difficulty = input.difficulty,
            authorName = input.author.user,
            datePublished = input.author.datePublished,
            description = input.description,
            ingredient = input.ingredient,
            step = input.step.map {
                it.substringAfter(" ")
            }
        )

    fun mapListRecipesDetailEntityToDomain(input: List<RecipesDetailEntity>) =
        input.map {
            RecipesDetail(
                title = it.title,
                thumbnail = it.thumbnail,
                key = it.key,
                times = it.times,
                portion = it.portion,
                difficulty = it.difficulty,
                authorName = it.authorName,
                datePublished = it.datePublished,
                description = it.description,
                ingredient = jsonToList(it.ingredient),
                step = jsonToList(it.step)
            )
        }

    fun mapRecipesDetailDomainToEntity(input: RecipesDetail) =
        RecipesDetailEntity(
            title = input.title,
            thumbnail = input.thumbnail,
            key = input.key,
            times = input.times,
            portion = input.portion,
            difficulty = input.difficulty,
            authorName = input.authorName,
            datePublished = input.datePublished,
            description = input.description,
            ingredient = listToJson(input.ingredient),
            step = listToJson(input.step)
        )

    fun mapRecipesDetailEntityToDomain(input: RecipesDetailEntity) =
        RecipesDetail(
            title = input.title,
            thumbnail = input.thumbnail,
            key = input.key,
            times = input.times,
            portion = input.portion,
            difficulty = input.difficulty,
            authorName = input.authorName,
            datePublished = input.datePublished,
            description = input.description,
            ingredient = jsonToList(input.ingredient),
            step = jsonToList(input.step)
        )
}