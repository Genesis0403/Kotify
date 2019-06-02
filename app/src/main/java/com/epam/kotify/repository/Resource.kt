package com.epam.kotify.repository

data class Resource<T>(
    var status: Status,
    var data: T?,
    var message: String?
) {

    companion object {
        fun <T> success(data: T) = Resource(Status.SUCCESS, data, null)

        fun <T> error(message: String?, data: T?) = Resource(Status.ERROR, data, message)

        fun <T> loading(data: T?) = Resource(Status.LOADING, data, null)
    }
}