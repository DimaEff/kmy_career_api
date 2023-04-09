package ru.my_career.roles.services

import org.litote.kmongo.Id
import ru.my_career.roles.models.CreateRoleDto
import ru.my_career.roles.models.Role

interface RoleService {
    suspend fun createRole(dto: CreateRoleDto): Role?
    suspend fun findRole(id: Id<Role>): Role?
}