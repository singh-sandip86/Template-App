package com.example.templateapp.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.templateapp.common.LoadingEvent
import com.example.templateapp.network.ErrorResponse
import com.example.templateapp.network.Event
import com.example.templateapp.utils.ErrorUtils
import org.greenrobot.eventbus.EventBus
import retrofit2.Response

abstract class BaseViewModel(internal val errorUtils: ErrorUtils): ViewModel() {

    companion object {
        internal const val GENERIC_ERROR_MESSAGE = "Something went wrong"
    }

    fun startLoading() {
        EventBus.getDefault().post(LoadingEvent(true))
    }

    fun stopLoading() {
        EventBus.getDefault().post(LoadingEvent(false))
    }

    /* Error messages from API calls */
    private val _error = MutableLiveData<Event<ErrorResponse>>()
    fun error(): LiveData<Event<ErrorResponse>> = _error

    /**
     *  Parse the error from API response to our model ErrorResponse
     *  Post it to LiveData for observers to check and handle errors on UI side
     */
    fun handleError(response: Response<*>): ErrorResponse {
        val errorValue = errorUtils.parseError(response)
        return handleError(errorValue)
    }

    /*
    * If error is parsed already then we can not parse it again as "errorBody()" will return null
    * In that cases we need to use below function and send the previously parsed result
    * */
    fun handleError(errorResponse: ErrorResponse): ErrorResponse {
        setError(errorResponse)
        return errorResponse
    }

    private fun setError(errorResponse: ErrorResponse) {
        _error.postValue(Event(errorResponse))
    }
}