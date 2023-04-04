package ru.my_career.roles.services

import ru.my_career.config.MongoDB
import ru.my_career.roles.models.Permission

class PermissionServiceImpl(private val db: MongoDB) : PermissionService {
    private val permissionRepository = db.getCollection<Permission>()

    override fun test(): String = "test123"

    override suspend fun createPermission(permission: Permission): String? {
        @Suppress("NAME_SHADOWING")
        val permission = permissionRepository.insertOne(permission)
        return permission.insertedId?.toString()
    }
}