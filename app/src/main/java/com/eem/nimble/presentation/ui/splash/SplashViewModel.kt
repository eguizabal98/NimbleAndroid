package com.eem.nimble.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eem.domain.interactor.auth.IsUserLoginUseCase
import com.eem.domain.model.base.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isUserLoginUseCase: IsUserLoginUseCase
) : ViewModel() {

    var baseEvent = MutableSharedFlow<Any>()

    fun isUserLogin() {
        viewModelScope.launch {
            isUserLoginUseCase().onSuccess {
                if (it) {
                    baseEvent.emit(BaseEvent.OnNavigateToHome)
                } else {
                    baseEvent.emit(BaseEvent.OnNavigateToLogin)
                }
            }
        }
    }

    sealed class BaseEvent {
        object OnNavigateToHome : BaseEvent()
        object OnNavigateToLogin : BaseEvent()
    }
}