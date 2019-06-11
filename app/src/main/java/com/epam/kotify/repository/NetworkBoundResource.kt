package com.epam.kotify.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.epam.kotify.api.ApiErrorResponse
import com.epam.kotify.api.ApiResponse
import com.epam.kotify.api.ApiSuccessResponse
import com.epam.kotify.utils.AppExecutors

/**
 * Class which take care of Network and DB work with [MediatorLiveData].
 * Fetches data if needed or load it from DB.
 *
 * @param ResultType
 * @param RequestType
 *
 * @see ApiResponse
 *
 * @author Vlad Korotkevich
 */

abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()
    private val mappedValue = MutableLiveData<ResultType>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        if (shouldFetch()) {
            fetchFromNetwork()
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        mappedValue.postValue(processResponse(response))
                    }
                    appExecutors.mainThread().execute {
                        result.addSource(mappedValue) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    setValue(Resource.error(response.message, null))
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected abstract fun processResponse(response: ApiSuccessResponse<RequestType>): ResultType

    @MainThread
    protected abstract fun shouldFetch(): Boolean

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}