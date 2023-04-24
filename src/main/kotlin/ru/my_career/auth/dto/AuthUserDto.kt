package ru.my_career.auth.dto

import kotlinx.serialization.Serializable
import ru.my_career._common.confirmations.ConfirmationStatus
import ru.my_career._common.users.dto.UserDto
import ru.my_career.companies.dto.CompanyDto

@Serializable
data class AuthUserDto(
    val confirmationStatus: ConfirmationStatus?,
    val jwtToken: String? = null,
    val user: UserDto? = null,
    val company: CompanyDto? = null
)
