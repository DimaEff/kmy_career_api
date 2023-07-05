package ru.my_career.companies.services

import ru.my_career._common.database.Id
import ru.my_career._common.types.ResponseEntity
import ru.my_career._common.users.dto.UserDto
import ru.my_career.companies.dto.CompanyDto
import ru.my_career.companies.dto.CreateCompanyDto
import ru.my_career.roles.dto.AddRoleToUserDto

interface CompaniesService {
    fun createCompany(dto: CreateCompanyDto, userId: Id): ResponseEntity<CompanyDto>
    fun getUserCompanies(userId: Id): ResponseEntity<Collection<CompanyDto>>
    fun getCompanyUsers(companyId: Id): ResponseEntity<Collection<UserDto>>
    fun getCompanyById(companyId: Id): ResponseEntity<CompanyDto>
    fun addUserToCompany(companyId: Id, phoneNumber: String, roleId: Id): ResponseEntity<String>
}
