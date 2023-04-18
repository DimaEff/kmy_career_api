package ru.my_career.roles.services

import ru.my_career._common.database.Id
import ru.my_career._common.types.ResponseEntity
import ru.my_career.auth.dto.JwtInfo
import ru.my_career.roles.dto.*

interface RolesService {
    fun createRole(dto: CreateRoleDto, jwtInfo: JwtInfo): ResponseEntity<RoleDto>
    fun getUserRolesForCompany(jwtInfo: JwtInfo): ResponseEntity<Collection<RoleDto>>
    fun getRoleById(id: Id): ResponseEntity<RoleDto>
    fun getRolePermissions(id: Id): ResponseEntity<Collection<PermissionDto>>
    fun addPermissionsToRole(dto: UpdateRolePermissionsDto): ResponseEntity<String>
    fun removePermissionFromRole(dto: UpdateRolePermissionsDto): ResponseEntity<String>
    fun addCommonRolePermissions(dto: CreateUpdateCommonRolePermissionsDto): ResponseEntity<String>
    fun removePermissionsFromCommonRole(dto: CreateUpdateCommonRolePermissionsDto): ResponseEntity<String>
}