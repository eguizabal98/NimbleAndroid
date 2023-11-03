package com.eem.data.network.model.survey

import com.google.gson.annotations.SerializedName

data class SurveyAnswerBody(
    @SerializedName("survey_id")
    val surveyId: String = "",
    @SerializedName("questions")
    val questions: List<Questions> = emptyList()
)

data class Questions(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("answers")
    val answers: List<Answer> = emptyList()
)

data class Answer(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("answer")
    val answer: String = ""
)
