package ru.my_career.users.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserDto(
    val firstname: String,
    val surname: String,
    val phoneNumber: String,
    val email: String,
)
