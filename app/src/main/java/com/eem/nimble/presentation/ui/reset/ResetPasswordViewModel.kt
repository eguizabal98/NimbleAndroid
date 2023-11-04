package com.eem.nimble.presentation.ui.reset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eem.domain.interactor.auth.ForgotPasswordUseCase
import com.eem.domain.model.base.onFailure
import com.eem.domain.model.base.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {

    var uiState by mutableStateOf(UIState())
        private set

    private fun resetPassword() {
        uiState = uiState.copy(loading = true)
        viewModelScope.launch {
            resetPasswordUseCase(uiState.email).onSuccess {
                uiState = uiState.copy(loading = false)
                uiState = uiState.copy(successMessage = it)
            }.onFailure {
                uiState = uiState.copy(loading = false)
                uiState = uiState.copy(loginError = it.message)
            }
        }
    }

    fun onUIEvent(event: UIEvent) {
        when (event) {
            UIEvent.OnResetClick -> resetPassword()
            is UIEvent.OnUserEmailChange -> {
                uiState = uiState.copy(email = event.email)
            }
            UIEvent.OnClearMessage -> {
                uiState = uiState.copy(successMessage = "")
            }
        }
    }

    sealed class UIEvent {
        object OnResetClick : UIEvent()
        data class OnUserEmailChange(val email: String) : UIEvent()
        object OnClearMessage : UIEvent()
    }

    data class UIState(
        val loading: Boolean = false,
        val email: String = "",
        val loginError: String = "",
        val successMessage: String = ""
    )

    data class ResetUIEventActions(
        val resetClick: () -> Unit = {},
        val userEmailChange: (String) -> Unit = {},
        val clearMessage: () -> Unit = {}
    )
}