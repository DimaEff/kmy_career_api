package ru.my_career.roles.services

import ru.my_career._common.database.Id
import ru.my_career._common.types.ResponseEntity
import ru.my_career.roles.dto.CreateRoleDto
import ru.my_career.roles.dto.PermissionDto
import ru.my_career.roles.dto.RoleDto
import ru.my_career.roles.dto.UpdateRolePermissionsDto

interface RolesService {
    fun getAllRoles(): ResponseEntity<Collection<RoleDto>>
    fun getRoleById(id: Id): ResponseEntity<RoleDto>
    fun getRolePermissions(id: Id): ResponseEntity<Collection<PermissionDto>>
    fun addPermissionsToRole(dto: UpdateRolePermissionsDto): ResponseEntity<String>
    fun removePermissionFromRole(dto: UpdateRolePermissionsDto): ResponseEntity<String>
    fun createRole(dto: CreateRoleDto): ResponseEntity<RoleDto>
}