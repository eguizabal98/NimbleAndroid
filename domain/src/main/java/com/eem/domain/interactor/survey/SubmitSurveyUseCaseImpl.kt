package com.eem.domain.interactor.survey

import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.model.survey.SurveyAnswer
import com.eem.domain.repository.survey.SurveyRepository
import javax.inject.Inject

class SubmitSurveyUseCaseImpl @Inject constructor(
    private val surveyRepository: SurveyRepository
) : SubmitSurveyUseCase {
    override suspend fun invoke(surveyAnswer: SurveyAnswer): ResultWrapper<Boolean> =
        surveyRepository.submitSurveyResponse(surveyAnswer)
}