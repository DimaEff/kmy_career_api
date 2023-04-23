package ru.my_career._common.users.dto

import kotlinx.serialization.Serializable
import ru.my_career._common.database.Id

@Serializable
data class UserDto(
    val id: Id,
    val firstname: String,
    val surname: String,
    val phoneNumber: String,
    val email: String,
)
