package com.eem.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccessTokenEntity(
    @PrimaryKey
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val refreshToken: String,
    val createdAt: Long
)
