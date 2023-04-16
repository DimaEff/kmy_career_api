package ru.my_career.roles.services

import io.ktor.http.*
import ru.my_career._common.types.ResponseEntity
import ru.my_career.roles.dto.CreatePermissionDto
import ru.my_career.roles.dto.PermissionDto
import ru.my_career.roles.repositories.PermissionsRepository
import ru.my_career.roles.repositories.toDto

class PermissionsServiceImpl(
    private val permissionsRepository: PermissionsRepository
) : PermissionsService {
    override fun createPermission(dto: CreatePermissionDto): ResponseEntity<PermissionDto> {
        val title = getPermissionTitle(dto)
        val permissionById = permissionsRepository.findByTitle(title)
        if (permissionById != null) {
            return ResponseEntity(
                statusCode = HttpStatusCode.BadRequest,
                errorMessage = "The permission with title `${title}` already exists"
            )
        }

        val permissionToCreate = PermissionDto(title = title, description = dto.description)
        val permission = permissionsRepository.createPermission(permissionToCreate)
            ?: return ResponseEntity(
                statusCode = HttpStatusCode.InternalServerError,
                errorMessage = "Error while a create permission"
            )

        return ResponseEntity(HttpStatusCode.OK, payload = permission.toDto())
    }

    private fun getPermissionTitle(dto: CreatePermissionDto): String = "${dto.title}:${dto.permissionType}"
}