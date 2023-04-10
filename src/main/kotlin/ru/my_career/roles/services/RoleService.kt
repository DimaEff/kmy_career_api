package ru.my_career.roles.services

import org.litote.kmongo.Id
import ru.my_career.common.db.MongoId
import ru.my_career.companies.models.Company
import ru.my_career.roles.models.CreateRoleDto
import ru.my_career.roles.models.Role
import ru.my_career.users.models.User

interface RoleService {
    suspend fun createRole(dto: CreateRoleDto): Role?
    suspend fun getRolesByIds(ids: Collection<String>): Collection<Role>
    suspend fun findRole(id: String): Role?
    suspend fun createOwnerRoleForCompany(companyId: MongoId<Company>): Role?
}