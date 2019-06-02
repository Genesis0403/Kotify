package com.epam.kotify.repository

enum class Status {
    SUCCESS,
    ERROR,
    LOADING;

    fun isLoading() = this == LOADING
}