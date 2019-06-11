package com.epam.kotify.repository

/**
 * Enum with API statuses.
 *
 * @see Resource
 *
 * @author Vlad Korotkevich
 */

enum class Status {
    SUCCESS,
    ERROR,
    LOADING;

    fun isLoading() = this == LOADING
}