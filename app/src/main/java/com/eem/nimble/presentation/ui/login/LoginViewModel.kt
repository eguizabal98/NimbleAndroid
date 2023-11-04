package com.eem.nimble.presentation.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eem.domain.interactor.auth.LoginUserUseCase
import com.eem.domain.model.auth.LoginUser
import com.eem.domain.model.base.onFailure
import com.eem.domain.model.base.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    var uiState by mutableStateOf(UIState())
        private set

    var baseEvent = MutableSharedFlow<Any>()

    private fun executeLogin() {
        uiState = uiState.copy(loading = true)
        viewModelScope.launch {
            loginUserUseCase(LoginUser(uiState.email, uiState.password)).onSuccess {
                uiState = uiState.copy(loading = false)
                navigateToHome()
            }.onFailure {
                uiState = uiState.copy(loading = false)
                uiState = uiState.copy(loginError = it.message)
            }
        }
    }

    private fun navigateToHome() {
        viewModelScope.launch { baseEvent.emit(BaseEvent.OnNavigateToHome) }
    }

    private fun onUserEmailValueChange(email: String) {
        uiState = uiState.copy(email = email, loginError = "")
    }

    private fun onUserPasswordValueChange(password: String) {
        uiState = uiState.copy(password = password, loginError = "")
    }

    fun onUIEvent(event: UIEvent) {
        when (event) {
            UIEvent.OnLoginClick -> executeLogin()
            is UIEvent.OnUserEmailChange -> onUserEmailValueChange(event.email)
            is UIEvent.OnUserPasswordChange -> onUserPasswordValueChange(event.password)
        }
    }

    sealed class UIEvent {
        object OnLoginClick : UIEvent()
        data class OnUserEmailChange(val email: String) : UIEvent()
        data class OnUserPasswordChange(val password: String) : UIEvent()
    }

    data class UIState(
        val loading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val loginError: String = ""
    )

    data class LoginUIEventActions(
        val loginClick: () -> Unit = {},
        val userEmailChange: (String) -> Unit = {},
        val userPasswordChange: (String) -> Unit = {},
        val forgotPasswordClick: () -> Unit = {}
    )

    sealed class BaseEvent {
        object OnNavigateToHome : BaseEvent()
    }
}