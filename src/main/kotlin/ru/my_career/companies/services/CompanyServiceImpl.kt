package ru.my_career.companies.services

import com.mongodb.client.model.PushOptions
import org.bson.types.ObjectId
import org.litote.kmongo.pushEach
import org.litote.kmongo.setValue
import org.litote.kmongo.util.idValue
import ru.my_career.auth.models.JwtInfo
import ru.my_career.common.db.MongoId
import ru.my_career.companies.models.Company
import ru.my_career.companies.models.CompanyUserRole
import ru.my_career.companies.models.CreateCompanyDto
import ru.my_career.config.MongoDB
import ru.my_career.roles.models.Role
import ru.my_career.roles.services.RoleService
import ru.my_career.users.models.User
import ru.my_career.users.services.UsersService

class CompanyServiceImpl(
    db: MongoDB,
    private val rolesService: RoleService,
    private val usersService: UsersService
) : CompanyService {
    private val companyCollection = db.getCollection<Company>()
    private val companyUserRolesCollection = db.getCollection<CompanyUserRole>()

    override suspend fun createCompany(dto: CreateCompanyDto, jwtInfo: JwtInfo): Company? {
        val user = usersService.getUserById(jwtInfo.userId) ?: return null

        val company = Company(title = dto.title, roles = emptySet())
        companyCollection.insertOne(company)

        val role = rolesService.createOwnerRoleForCompany(company._id) ?: return null
        addRole(company._id.toString(), setOf(role._id.toString()))
        val companyWithRoles = getCompanyById(company._id.toString()) ?: return null

        createCompanyUserRole(companyWithRoles._id, user._id, role._id)

        return companyWithRoles
    }

    override suspend fun getCompanyById(companyId: String): Company? {
        return try {
            companyCollection.findOneById(ObjectId(companyId))
        } catch (e: Throwable) {
            null
        }
    }

    private suspend fun addRole(companyId: String, rolesIds: Collection<String>): Unit {
        val roles = rolesService.getRolesByIds(rolesIds)

        companyCollection.updateOneById(
            ObjectId(companyId),
            pushEach(Company::roles, roles.map { it._id })
        )
    }

    private suspend fun createCompanyUserRole(
        companyId: MongoId<Company>,
        userId: MongoId<User>,
        roleId: MongoId<Role>
    ): Unit {
        companyUserRolesCollection.insertOne(CompanyUserRole(companyId = companyId, userId = userId, roleId = roleId))
    }
}