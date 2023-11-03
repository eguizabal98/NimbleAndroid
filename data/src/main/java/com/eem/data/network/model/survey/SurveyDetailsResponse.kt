package com.eem.data.network.model.survey

import com.google.gson.annotations.SerializedName


data class SurveyDetailsResponse(

    @SerializedName("data")
    var surveyData: SurveyData,
    @SerializedName("included")
    var included: List<Included> = emptyList()
)

data class Included(
    @SerializedName("id")
    var id: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("attributes")
    var attributes: Attributes,
    @SerializedName("relationships")
    var relationships: AnswerRelationships
)

data class Attributes(
    @SerializedName("text")
    var text: String = "",
    @SerializedName("help_text")
    var helpText: String = "",
    @SerializedName("display_order")
    var displayOrder: Int = 0,
    @SerializedName("short_text")
    var shortText: String = "",
    @SerializedName("pick")
    var pick: String = "",
    @SerializedName("display_type")
    var displayType: String = "",
    @SerializedName("is_mandatory")
    var isMandatory: Boolean = false,
    @SerializedName("correct_answer_id")
    var correctAnswerId: String = "",
    @SerializedName("facebook_profile")
    var facebookProfile: String = "",
    @SerializedName("twitter_profile")
    var twitterProfile: String = "",
    @SerializedName("image_url")
    var imageUrl: String = "",
    @SerializedName("cover_image_url")
    var coverImageUrl: String = "",
    @SerializedName("cover_image_opacity")
    var coverImageOpacity: Double = 0.0,
    @SerializedName("cover_background_color")
    var coverBackgroundColor: String = "",
    @SerializedName("is_shareable_on_facebook")
    var isShareableOnFacebook: Boolean = false,
    @SerializedName("is_shareable_on_twitter")
    var isShareableOnTwitter: Boolean = false,
    @SerializedName("font_face")
    var fontFace: String = "",
    @SerializedName("font_size")
    var fontSize: String = "",
    @SerializedName("tag_list")
    var tagList: String = ""
)

data class AnswerRelationships(
    @SerializedName("answers")
    val surveyAnswers: SurveyAnswers
)

data class SurveyAnswers(
    @SerializedName("data")
    val data: List<AnswerData> = emptyList()
)

data class AnswerData(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String
)