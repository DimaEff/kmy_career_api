package ru.my_career.roles.services

import io.ktor.http.*
import ru.my_career._common.database.Id
import ru.my_career._common.types.ResponseEntity
import ru.my_career.roles.CommonRoleTitle
import ru.my_career.roles.dto.*
import ru.my_career.roles.repositories.PermissionsRepository
import ru.my_career.roles.repositories.RolesRepository
import ru.my_career.roles.repositories.toDto

class RolesServiceImpl(
    private val rolesRepository: RolesRepository,
    private val permissionsRepository: PermissionsRepository
) : RolesService {
    override fun getAllRoles(): ResponseEntity<Collection<RoleDto>> {
        val roles = rolesRepository.getAllRoles()
        return if (roles == null) {
            ResponseEntity(HttpStatusCode.InternalServerError, errorMessage = "Failed to get all roles")
        } else {
            ResponseEntity(payload = roles.map { it.toDto() })
        }
    }

    override fun createRole(dto: CreateRoleDto, companyId: Id, userId: Id): ResponseEntity<RoleDto> {
        val commonRolePermissionsIds =
            if (dto.commonRoleTitle != null) rolesRepository.getPermissionsForCommonRole(CommonRoleTitle.valueOf(dto.commonRoleTitle)) else emptySet()
        val permissionsIds = (dto.permissions + commonRolePermissionsIds).distinct()

        val permissions = permissionsRepository.checkIsAllPermissionsExistsAndGetPermissions(permissionsIds)
            ?: return ResponseEntity(
                HttpStatusCode.BadRequest,
                errorMessage = "Invalid permissions ids collections"
            )

        val role = rolesRepository.createRole(dto, permissions, companyId, userId)
        return if (role == null) {
            ResponseEntity(HttpStatusCode.InternalServerError, errorMessage = "Error while create a role")
        } else {
            ResponseEntity(HttpStatusCode.Created, payload = role.toDto())
        }
    }

    override fun getRoleById(id: Id): ResponseEntity<RoleDto> {
        val role = rolesRepository.getRoleById(id)
        return if (role == null) {
            ResponseEntity(HttpStatusCode.NotFound, errorMessage = "Role not found")
        } else {
            ResponseEntity(payload = role.toDto())
        }
    }

    override fun getRolePermissions(id: Id): ResponseEntity<Collection<PermissionDto>> {
        val permissions = permissionsRepository.getAllPermissionsOfRole(id)
        return if (permissions == null) {
            ResponseEntity(HttpStatusCode.NotFound, errorMessage = "Role not found")
        } else {
            println(permissions)
            ResponseEntity(payload = permissions.map { it.toDto() })
        }
    }

    override fun addPermissionsToRole(dto: UpdateRolePermissionsDto): ResponseEntity<String> {
        permissionsRepository.checkIsAllPermissionsExistsAndGetPermissions(dto.permissions)
            ?: return ResponseEntity(HttpStatusCode.BadRequest, errorMessage = "Not all permissions exists")

        return if (rolesRepository.addPermissionToRole(dto) == null) {
            ResponseEntity(HttpStatusCode.InternalServerError, "Failed adding permission to the role ${dto.roleId}")
        } else {
            ResponseEntity(payload = "Success added")
        }
    }

    override fun removePermissionFromRole(dto: UpdateRolePermissionsDto): ResponseEntity<String> {
        return if (rolesRepository.removePermissionsFromRole(dto) == null) {
            ResponseEntity(
                HttpStatusCode.InternalServerError,
                errorMessage = "Failed removing permission from role ${dto.roleId}"
            )
        } else {
            ResponseEntity(payload = "Success removing")
        }
    }

    override fun addCommonRolePermissions(dto: CreateUpdateCommonRolePermissionsDto): ResponseEntity<String> {
        return if (rolesRepository.addCommonRolePermissions(dto) == null) {
            ResponseEntity(
                HttpStatusCode.InternalServerError,
                errorMessage = "Failed creating a common role permissions"
            )
        } else {
            ResponseEntity(payload = "Success adding")
        }
    }

    override fun removePermissionsFromCommonRole(dto: CreateUpdateCommonRolePermissionsDto): ResponseEntity<String> {
        return if (rolesRepository.removePermissionsFromCommonRole(dto) == null) {
            ResponseEntity(
                HttpStatusCode.InternalServerError,
                errorMessage = "Failed removing a common role permissions"
            )
        } else {
            ResponseEntity(payload = "Success removing")
        }
    }
}