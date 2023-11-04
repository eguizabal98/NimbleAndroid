package com.eem.domain.model.survey

data class SurveyData(
    val id: String,
    val type: String,
    val attributes: SurveyAttributes?,
    val relationships: SurveyRelationships?
)

data class SurveyAttributes(
    val title: String? = "",
    val description: String? = "",
    val emailAboveData: String? = "",
    val emailBellowData: String? = "",
    val isActive: String? = "",
    val imageUrl: String? = "",
    val createdAt: String? = "",
    val activeAt: String? = "",
    val inactiveAt: String? = "",
    val surveyType: String? = ""
)

data class SurveyRelationships(
    val surveyQuestions: SurveyQuestions
)

data class SurveyQuestions(
    val data: List<QuestionData>? = emptyList()
)

data class QuestionData(
    val id: String? = "",
    val type: String? = ""
)
