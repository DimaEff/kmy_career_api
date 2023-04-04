package ru.my_career.roles.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Role(
    @BsonId
    val id: Id<Role>? = null,
    val title: String,
)

@Serializable
data class RoleDto(
    val title: String
)

fun Role.toDto() = RoleDto(title)
fun RoleDto.toModel() = Role(title = this.title)
