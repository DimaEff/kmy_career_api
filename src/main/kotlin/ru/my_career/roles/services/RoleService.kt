package ru.my_career.roles.services

import ru.my_career.roles.models.CreateRoleDto
import ru.my_career.roles.models.Role

interface RoleService {
    suspend fun createRole(dto: CreateRoleDto): Role?
    suspend fun getRolesByIds(ids: Collection<String>): Collection<Role>
    suspend fun findRole(id: String): Role?
    suspend fun createOwnerRoleForCompany(companyId: String): Role?
}