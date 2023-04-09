package ru.my_career.roles.services

import org.litote.kmongo.Id
import ru.my_career.roles.models.CreatePermissionDto
import ru.my_career.roles.models.Permission

interface PermissionService {
    suspend fun getPermissionById(id: String?): Permission?
    suspend fun deletePermissionById(id: String): Unit
    suspend fun createPermission(dto: CreatePermissionDto): Permission?
    suspend fun getPermissionsByIds(ids: Collection<String>): Collection<Permission>
}