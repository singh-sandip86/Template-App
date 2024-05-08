package com.example.templateapp.ui.screen.launcher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.templateapp.R
import com.example.templateapp.common.Validation
import com.example.templateapp.dto.response.login.toUser
import com.example.templateapp.model.login.LoginError
import com.example.templateapp.model.login.User
import com.example.templateapp.ui.base.BaseViewModel
import com.example.templateapp.ui.screen.launcher.repository.LoginRepository
import com.example.templateapp.utils.ErrorUtils
import com.example.templateapp.utils.ResourceProvider
import com.example.templateapp.utils.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val resourceProvider: ResourceProvider,
    errorUtils: ErrorUtils
) : BaseViewModel(errorUtils) {

    private val _emailFlow = MutableStateFlow(String.empty())
    val emailFlow = _emailFlow.asStateFlow()
    fun onEmailChange(text: CharSequence) = viewModelScope.launch {
        _validateAndSignIn.emit(false)
        _emailFlow.value = text.toString()
    }

    private val _passwordFlow = MutableStateFlow(String.empty())
    val passwordFlow = _passwordFlow.asStateFlow()
    fun onPasswordChange(text: CharSequence) = viewModelScope.launch {
        _validateAndSignIn.emit(false)
        _passwordFlow.value = text.toString()
    }

    private val _validateAndSignIn = MutableSharedFlow<Boolean>()
    val validateAndSignIn = _validateAndSignIn.asSharedFlow()
    fun onLoginClick() = viewModelScope.launch {
        _validateAndSignIn.emit(true)
    }

    val loginError: LiveData<LoginError> =
        combine(_emailFlow, _passwordFlow, _validateAndSignIn) { email, password, validate ->
            validate(email, password, validate)
        }.asLiveData()

    fun validate(email: String, password: String, validate: Boolean): LoginError {
        return if (validate) {
            var isEmailInvalid = email.isEmpty()
            var errorEmail = ""
            if (isEmailInvalid) {
                errorEmail = resourceProvider.getString(R.string.error_email_id_required)
            } else {
                isEmailInvalid = Validation.isValidEmailAddress(email).not()
                if (isEmailInvalid) {
                    errorEmail = resourceProvider.getString(R.string.error_invalid_email_address)
                }
            }
            var isPasswordInvalid = password.isEmpty()
            var errorPassword = ""
            if (isPasswordInvalid) {
                errorPassword = resourceProvider.getString(R.string.error_password_required)
            } else {
                isPasswordInvalid = password.length < 5
                if (isPasswordInvalid) {
                    errorPassword = resourceProvider.getString(R.string.error_invalid_password)
                }
            }

            if (isEmailInvalid || isPasswordInvalid) {
                LoginError(
                    isEmailInvalid,
                    errorEmail,
                    isPasswordInvalid,
                    errorPassword
                )
            } else {
                logIn()
                LoginError()
            }
        } else {
            LoginError()
        }
    }

    // API Calls
    private val _isLoading = MutableSharedFlow<Boolean>()
    val isLoading = _isLoading.asSharedFlow()

    private val _user = MutableSharedFlow<Result<User>>()
    val user = _user.asSharedFlow()

    fun logIn() = viewModelScope.launch {
        _isLoading.emit(true)
        loginRepository.loginUser(_emailFlow.value, _passwordFlow.value).let { response ->
            if (response.isSuccessful) {
                response.body().let {
                    it?.data?.let {
                        _user.emit(Result.success(it.toUser()))
                    } ?: run {
                        LoginError(
                            true,
                            it?.message ?: GENERIC_ERROR_MESSAGE,
                            false,
                            ""
                        )
                    }
                }
            } else {
                handleError(response)
            }
        }
        _isLoading.emit(false)
    }
}