package com.dorizu.masako.core.utils

import com.google.gson.Gson

object Converters {

    fun listToJson(value: List<String>?) = Gson().toJson(value)

    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}