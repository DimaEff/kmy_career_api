package ru.my_career.roles.dto

import kotlinx.serialization.Serializable
import ru.my_career._common.database.Id

@Serializable
data class CreateUpdateCommonRolePermissionsDto(
    val commonRoleTitle: String,
    val permissions: Collection<Id>,
)
