package ru.my_career.roles.services

import ru.my_career._common.types.ResponseEntity
import ru.my_career.roles.dto.CreatePermissionDto
import ru.my_career.roles.dto.PermissionDto

interface PermissionsService {
    fun getAllPermissions(): ResponseEntity<Collection<PermissionDto>>
    fun createPermission(dto: CreatePermissionDto): ResponseEntity<PermissionDto>
}
