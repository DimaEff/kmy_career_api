package ru.my_career.companies.services

import org.bson.types.ObjectId
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

        val company = Company(title = dto.title)
        companyCollection.insertOne(company)

        val role = rolesService.createOwnerRoleForCompany(company._id) ?: return null
        createCompanyUserRole(company._id, user._id, role._id)

        return company
    }

    override suspend fun getCompanyById(companyId: String): Company? {
        return try {
            companyCollection.findOneById(ObjectId(companyId))
        } catch (e: Throwable) {
            null
        }
    }

    private suspend fun createCompanyUserRole(companyId: MongoId<Company>, userId: MongoId<User>, roleId: MongoId<Role>): Unit {
        companyUserRolesCollection.insertOne(CompanyUserRole(companyId = companyId, userId = userId, roleId = roleId))
    }
}