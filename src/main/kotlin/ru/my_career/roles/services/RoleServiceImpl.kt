package ru.my_career.roles.services

import org.litote.kmongo.Id
import ru.my_career.config.MongoDB
import ru.my_career.roles.models.CreateRoleDto
import ru.my_career.roles.models.Role

class RoleServiceImpl(
    db: MongoDB,
    private val permissionService: PermissionService
) : RoleService {
    private val rolesRepository = db.getCollection<Role>()

    override suspend fun createRole(dto: CreateRoleDto): Role? {
        return try {
            val permissions = permissionService.getPermissionsByIds(dto.permissions)
            val role = Role(
                title = dto.title,
                description = dto.description,
                permissions = permissions.map { it._id }
            )
            rolesRepository.insertOne(role)
            role
        } catch (e: Throwable) {
            null
        }
    }

    override suspend fun findRole(id: Id<Role>): Role? {
        return rolesRepository.findOneById(id)
    }
}