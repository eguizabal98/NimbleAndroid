package com.eem.domain.model.auth

data class RegisterUser(
    val email: String,
    val name: String,
    val password: String,
    val passwordConfirmation: String
)
