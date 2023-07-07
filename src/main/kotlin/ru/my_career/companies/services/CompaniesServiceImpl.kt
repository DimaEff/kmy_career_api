package ru.my_career.companies.services

import io.ktor.http.*
import ru.my_career._common.database.Id
import ru.my_career._common.types.ResponseEntity
import ru.my_career._common.users.dto.UserDto
import ru.my_career._common.users.services.UsersService
import ru.my_career.companies.dto.CompanyDto
import ru.my_career.companies.dto.CreateCompanyDto
import ru.my_career.companies.repositories.CompaniesRepository
import ru.my_career.companies.repositories.toDto
import ru.my_career.roles.CommonRoleTitle
import ru.my_career.roles.dto.CreateUpdateRoleDto
import ru.my_career.roles.services.RolesService

class CompaniesServiceImpl(
    private val rolesService: RolesService,
    private val companiesRepository: CompaniesRepository,
    private val usersService: UsersService
) : CompaniesService {
    override fun getAllCompanies(): ResponseEntity<Collection<CompanyDto>> {
        val companies = companiesRepository.getAllCompanies() ?: return ResponseEntity(
            HttpStatusCode.BadRequest,
            errorMessage = "Some error with getting all companies"
        )
        return ResponseEntity(payload = companies.map { it.toDto() })
    }

    override fun createCompany(dto: CreateCompanyDto, userId: Id): ResponseEntity<CompanyDto> {
        val company = companiesRepository.createCompany(dto) ?: return ResponseEntity(
            HttpStatusCode.InternalServerError,
            errorMessage = "Failed while create a company"
        )
        val role = rolesService.createRole(
            CreateUpdateRoleDto(
                title = "Owner",
                description = "Owner",
                permissions = emptySet(),
                commonRoleTitle = CommonRoleTitle.OWNER.toString()
            ),
            companyId = company.id.value,
            userId = userId
        )
        if (role.payload == null) return ResponseEntity(
            HttpStatusCode.InternalServerError,
            errorMessage = "Invalid creating an owner role"
        )

        return ResponseEntity(HttpStatusCode.Created, company.toDto())
    }

    override fun getUserCompanies(userId: Id): ResponseEntity<Collection<CompanyDto>> {
        val companies = companiesRepository.getUserCompanies(userId) ?: return ResponseEntity(
            HttpStatusCode.InternalServerError,
            errorMessage = "Failed"
        )
        return ResponseEntity(payload = companies.map { it.toDto() })
    }

    override fun getCompanyUsers(companyId: Id): ResponseEntity<Collection<UserDto>> {
        val usersIds = companiesRepository.getCompanyUsers(companyId) ?: return ResponseEntity(
            HttpStatusCode.BadRequest,
            errorMessage = "Invalid company id"
        )

        return usersService.getUsersByIds(usersIds)
    }


    override fun getCompanyById(companyId: Id): ResponseEntity<CompanyDto> {
        val company = companiesRepository.getCompanyById(companyId) ?: return ResponseEntity(
            HttpStatusCode.NotFound,
            errorMessage = "The company not found"
        )
        return ResponseEntity(payload = company.toDto())
    }

    override fun addUserToCompany(companyId: Id, phoneNumber: String, roleId: Id): ResponseEntity<String> {
        val user = usersService.getUserByPhone(phoneNumber)
        if (user.payload == null) {
            return ResponseEntity(HttpStatusCode.BadRequest, errorMessage = "The user not found by the phone number")
        }

        return rolesService.addRoleToUserForCompany(companyId, user.payload.id, roleId)
    }
}
