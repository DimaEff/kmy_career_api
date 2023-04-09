package ru.my_career.roles.models

import kotlinx.serialization.Serializable
import org.litote.kmongo.newId
import ru.my_career.common.db.MongoId

@Serializable
data class Permission(
    val _id: MongoId<Permission> = newId(),
    val title: String,
    val description: String,
)

@Serializable
data class CreatePermissionDto(
    val title: String,
    val description: String,
)
