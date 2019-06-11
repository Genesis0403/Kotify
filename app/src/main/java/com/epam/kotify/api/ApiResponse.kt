package com.epam.kotify.api

import retrofit2.Response

/**
 * Class which represents responses with error messages and data from API.
 *
 * @author Vlad Korotkevich
 */

sealed class ApiResponse<T> {
    companion object {

        fun <T> create(t: Throwable) = ApiErrorResponse<T>(t.message ?: "unknown error")

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMessage = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMessage ?: "unknown error")
            }
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()
data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()
data class ApiErrorResponse<T>(val message: String) : ApiResponse<T>()