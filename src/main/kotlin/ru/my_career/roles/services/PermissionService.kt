package ru.my_career.roles.services

import ru.my_career.roles.models.Permission

interface PermissionService {
    fun test(): String
    suspend fun createPermission(permission: Permission): String?
}