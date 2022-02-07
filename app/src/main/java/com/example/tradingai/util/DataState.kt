package com.example.tradingai.util

import java.lang.Exception

sealed class DataState<out R> {
    data class Success <out T> (val data: T): DataState<T>()
    data class Error (val exception: Exception) : DataState<Nothing>()
    data class PartialSuccess <out T> (val data: T): DataState<T>()
    object Loading : DataState<Nothing>()
}