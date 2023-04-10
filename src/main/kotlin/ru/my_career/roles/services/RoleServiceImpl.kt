package ru.my_career.roles.services

import org.litote.kmongo.Id
import org.litote.kmongo.set
import ru.my_career.common.db.MongoId
import ru.my_career.companies.models.Company
import ru.my_career.companies.services.CompanyService
import ru.my_career.config.MongoDB
import ru.my_career.roles.CommonRoleTitle
import ru.my_career.roles.models.CreateRoleDto
import ru.my_career.roles.models.Role
import ru.my_career.users.models.User

class RoleServiceImpl(
    db: MongoDB,
    private val permissionService: PermissionService,
    private val companiesService: CompanyService,
) : RoleService {
    private val rolesRepository = db.getCollection<Role>()

    override suspend fun createRole(dto: CreateRoleDto): Role? {
        return try {
            val permissions = permissionService.getPermissionsByIds(dto.permissions)
            val company = companiesService.getCompanyById(dto.companyId)
            val commonTitle = if (dto.commonTitle != null) CommonRoleTitle.valueOf(dto.commonTitle) else null

            if (company == null) {
                return null
            }

            val role = Role(
                title = dto.title,
                description = dto.description,
                permissions = permissions.map { it._id },
                companyId = company._id,
                commonTitle = commonTitle
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

    override suspend fun createOwnerRoleForCompany(companyId: MongoId<Company>): Role? {
        return createRole(
            CreateRoleDto(
                title = "OWNER",
                companyId = companyId.toString(),
                description = "The owner of the company",
                // TODO(): add the owner`s permissions
                permissions = setOf("64330bfd708f0b03994dc3b2", "6433116dfd71c8382706648c"),
                commonTitle = CommonRoleTitle.OWNER.toString()
                )
        )
    }
}