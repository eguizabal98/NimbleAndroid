package com.eem.domain.interactor.survey

import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.model.survey.SurveyDetails

interface GetSurveyDetailsUseCase {
    suspend operator fun invoke(surveyId: String): ResultWrapper<SurveyDetails>
}