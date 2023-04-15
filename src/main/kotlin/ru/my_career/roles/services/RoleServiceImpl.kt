package ru.my_career.roles.services

import org.bson.types.ObjectId
import ru.my_career.config.MongoDB
import ru.my_career.roles.CommonRoleTitle
import ru.my_career.roles.models.CreateRoleDto
import ru.my_career.roles.models.Role

class RoleServiceImpl(
    db: MongoDB,
    private val permissionService: PermissionService,
) : RoleService {
    private val rolesRepository = db.getCollection<Role>()

    override suspend fun createRole(dto: CreateRoleDto): Role? {
        return try {
            val permissions = permissionService.getPermissionsByIds(dto.permissions)
            val commonTitle = if (dto.commonTitle != null) CommonRoleTitle.valueOf(dto.commonTitle) else null

            val role = Role(
                title = dto.title,
                description = dto.description,
                companyId = ObjectId(dto.companyId),
                permissions = dto.permissions,
                commonTitle = commonTitle
            )
            rolesRepository.insertOne(role)
            role
        } catch (e: Throwable) {
            null
        }
    }

    override suspend fun getRolesByIds(ids: Collection<String>): Collection<Role> {
        val objectsIds = ids.map { "ObjectId('$it')" }
        val queryString = "{ \"_id\": { \$in: $objectsIds } }"
        return rolesRepository.find(queryString).toList()
    }

    override suspend fun findRole(id: String): Role? {
        return rolesRepository.findOneById(ObjectId(id))
    }

    override suspend fun createOwnerRoleForCompany(companyId: String): Role? {
        return createRole(
            CreateRoleDto(
                title = "OWNER",
                description = "The owner of the company",
                companyId = companyId,
                // TODO(): add the owner`s permissions
                permissions = setOf("64330bfd708f0b03994dc3b2", "6433116dfd71c8382706648c"),
                commonTitle = CommonRoleTitle.OWNER.toString()
                )
        )
    }
}