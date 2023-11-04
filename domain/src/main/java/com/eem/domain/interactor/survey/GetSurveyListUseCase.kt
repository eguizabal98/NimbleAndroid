package com.eem.domain.interactor.survey

import androidx.paging.PagingData
import com.eem.domain.model.survey.SurveyData
import kotlinx.coroutines.flow.Flow

interface GetSurveyListUseCase {
    suspend operator fun invoke(): Flow<PagingData<SurveyData>>
}