package ru.my_career.roles.models

import kotlinx.serialization.Serializable

data class Permission(
    val _id: String,
    val description: String,
)

@Serializable
data class PermissionDto(
    val title: String,
    val description: String,
)

fun Permission.toDto() = PermissionDto(title = this._id.toString(), description = this.description)
fun PermissionDto.toModel() = Permission(_id = this.title, description = this.description)