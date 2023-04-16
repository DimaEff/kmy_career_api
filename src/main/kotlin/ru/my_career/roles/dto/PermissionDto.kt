package ru.my_career.roles.dto

import kotlinx.serialization.Serializable
import ru.my_career._common.database.Id

@Serializable
data class PermissionDto(
    val id: Id? = null,
    val title: String,
    val description: String,
)
