package ru.my_career.roles.dto

import kotlinx.serialization.Serializable
import ru.my_career._common.database.Id
import ru.my_career.roles.CommonRoleTitle

@Serializable
open class RoleDto(
    val id: Id,
    val title: String,
    val description: String,
    val commonRoleTitle: CommonRoleTitle?,
)
