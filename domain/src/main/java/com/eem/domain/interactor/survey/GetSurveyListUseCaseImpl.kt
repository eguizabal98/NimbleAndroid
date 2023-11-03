package com.eem.domain.interactor.survey

import androidx.paging.PagingData
import com.eem.domain.model.survey.SurveyData
import com.eem.domain.repository.survey.SurveyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSurveyListUseCaseImpl @Inject constructor(
    private val surveyRepository: SurveyRepository
) : GetSurveyListUseCase {
    override suspend fun invoke(): Flow<PagingData<SurveyData>> = surveyRepository.getSurveyList()
}
