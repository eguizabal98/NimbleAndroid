package com.eem.nimble.presentation.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eem.domain.interactor.auth.LogOutUseCase
import com.eem.domain.interactor.survey.GetSurveyListUseCase
import com.eem.domain.model.base.onSuccess
import com.eem.domain.model.survey.SurveyData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSurveyListUseCase: GetSurveyListUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    var uiState by mutableStateOf(UIState())
        private set

    fun logOut(logOutAction: () -> Unit) {
        viewModelScope.launch {
            logOutUseCase().onSuccess {
                logOutAction()
            }
        }
    }

    suspend fun getSurveyList() {
        viewModelScope.launch {
            uiState = uiState.copy(
                surveyList = getSurveyListUseCase().cachedIn(viewModelScope)
            )
        }
    }

    fun stopLoading() {
        viewModelScope.launch {
            _isRefreshing.emit(false)
        }
    }

    data class UIState(
        val surveyList: Flow<PagingData<SurveyData>> = flowOf()
    )
}