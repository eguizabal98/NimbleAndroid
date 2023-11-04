package com.eem.domain.model.user

data class ProfileInfo(
    val id: String,
    val type: String,
    val attributes: UserAttributesInfo
)

data class UserAttributesInfo(
    val email: String,
    val name: String,
    val avatarUrl: String
)
