package ru.my_career.roles.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateUpdateRoleDto(
    val title: String,
    val description: String,
    val permissions: Collection<Int>,
    val commonRoleTitle: String?,
)
