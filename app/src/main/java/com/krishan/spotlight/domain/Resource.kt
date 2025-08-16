package com.krishan.spotlight.domain

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data object Loading : Resource<Nothing>()
    data class Error(val message: String, val errorCode: String? = null, val throwable: Throwable? = null) :
        Resource<Nothing>()
}