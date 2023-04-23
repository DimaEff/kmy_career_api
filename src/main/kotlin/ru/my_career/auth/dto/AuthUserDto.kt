package ru.my_career.auth.dto

import kotlinx.serialization.Serializable
import ru.my_career._common.confirmations.ConfirmationStatus
import ru.my_career._common.users.dto.UserDto

@Serializable
data class AuthUserDto(
    val confirmationStatus: ConfirmationStatus?,
    val user: UserDto? = null,
)
