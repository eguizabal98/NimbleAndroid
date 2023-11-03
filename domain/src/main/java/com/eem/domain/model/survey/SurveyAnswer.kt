package com.eem.domain.model.survey

data class SurveyAnswer(
    val surveyId: String = "",
    val questions: List<Questions> = emptyList()
)

data class Questions(
    val id: String = "",
    val answers: List<Answer> = emptyList()
)

data class Answer(
    val id: String = "",
    val answer: String = ""
)
