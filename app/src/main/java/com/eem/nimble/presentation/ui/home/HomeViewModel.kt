package com.eem.nimble.presentation.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eem.domain.interactor.survey.GetSurveyListUseCase
import com.eem.domain.model.survey.SurveyData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSurveyListUseCase: GetSurveyListUseCase
) : ViewModel() {

    var uiState by mutableStateOf(UIState())
        private set

    suspend fun getSurveyList() {
        viewModelScope.launch {
            uiState = uiState.copy(
                surveyList = getSurveyListUseCase().cachedIn(viewModelScope)
            )
        }
    }

    data class UIState(
        val surveyList: Flow<PagingData<SurveyData>> = flowOf()
    )
}