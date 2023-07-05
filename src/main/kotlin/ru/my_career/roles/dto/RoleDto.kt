package ru.my_career.roles.dto

import kotlinx.serialization.Serializable
import ru.my_career._common.database.Id
import ru.my_career.companies.dto.CompanyDto
import ru.my_career.roles.CommonRoleTitle

@Serializable
data class RoleDto(
    val id: Id,
    val title: String,
    val description: String,
    val permissions: Collection<PermissionDto>,
    val company: CompanyDto,
    val commonRoleTitle: CommonRoleTitle?,
)
