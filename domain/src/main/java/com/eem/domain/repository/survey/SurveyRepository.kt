package com.eem.domain.repository.survey

import androidx.paging.PagingData
import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.model.survey.SurveyAnswer
import com.eem.domain.model.survey.SurveyData
import com.eem.domain.model.survey.SurveyDetails
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {

    suspend fun getSurveyList(): Flow<PagingData<SurveyData>>
    suspend fun getSurveyDetails(surveyId: String): ResultWrapper<SurveyDetails>
    suspend fun submitSurveyResponse(surveyAnswer: SurveyAnswer): ResultWrapper<Boolean>
}
