package ru.my_career.roles.services

import ru.my_career.config.MongoDB
import ru.my_career.roles.models.Role

class RoleServiceImpl(
    private val db: MongoDB,
    private val permissionService: PermissionService
) : RoleService {
    private val rolesRepository = db.getCollection<Role>()

    override suspend fun createRole(role: Role): String? {
        @Suppress("NAME_SHADOWING")
        val role = rolesRepository.insertOne(role)
        val test = permissionService.test()
        return role.insertedId?.toString() + test
    }
}