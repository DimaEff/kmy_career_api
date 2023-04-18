package ru.my_career.companies.services

import io.ktor.http.*
import ru.my_career._common.database.Id
import ru.my_career._common.types.ResponseEntity
import ru.my_career.auth.dto.JwtInfo
import ru.my_career.companies.dto.CompanyDto
import ru.my_career.companies.dto.CreateCompanyDto
import ru.my_career.companies.repositories.CompaniesRepository
import ru.my_career.companies.repositories.toDto
import ru.my_career.roles.CommonRoleTitle
import ru.my_career.roles.dto.CreateRoleDto
import ru.my_career.roles.services.RolesService

class CompaniesServiceImpl(
    private val rolesService: RolesService,
    private val companiesRepository: CompaniesRepository,
) : CompaniesService {
    override fun createCompany(dto: CreateCompanyDto, userId: Id): ResponseEntity<CompanyDto> {
        val company = companiesRepository.createCompany(dto) ?: return ResponseEntity(
            HttpStatusCode.InternalServerError,
            errorMessage = "Failed while create a company"
        )
        val role = rolesService.createRole(
            CreateRoleDto(
                title = "Owner",
                description = "Owner",
                permissions = emptySet(),
                commonRoleTitle = CommonRoleTitle.OWNER.toString()
            ),
            JwtInfo(userId, company.id.value)
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
}
