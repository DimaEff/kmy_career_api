package ru.my_career.roles.dto

import kotlinx.serialization.Serializable
import ru.my_career.roles.CommonRoleTitle

@Serializable
data class CreateRoleDto(
    val title: String,
    val description: String,
    val permissions: Collection<Int>,
    val commonRoleTitle: String?,
)
