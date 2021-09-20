package com.dorizu.masako.core.data.source.remote.network

sealed class ApiResponse<out R> {
    data class Success<out T>(val data:T) : com.dorizu.masako.core.data.source.remote.network.ApiResponse<T>()
    data class Error(val errorMessage: String) : com.dorizu.masako.core.data.source.remote.network.ApiResponse<Nothing>()
    object Empty : com.dorizu.masako.core.data.source.remote.network.ApiResponse<Nothing>()
}