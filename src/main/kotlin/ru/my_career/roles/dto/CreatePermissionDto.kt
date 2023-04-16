package ru.my_career.roles.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreatePermissionDto(
    val permissionType: String,
    val title: String,
    val description: String,
)
