package com.epam.kotify.utils

/**
 * Interfaces which used in injection.
 * Checks for connectivity status.
 *
 * @author Vlad Korotkevich
 */

interface ConnectionManager {
    fun hasConnection(): Boolean
}