package ru.my_career.companies.services

import ru.my_career._common.database.Id
import ru.my_career._common.types.ResponseEntity
import ru.my_career.companies.dto.CompanyDto
import ru.my_career.companies.dto.CreateCompanyDto

interface CompaniesService {
    fun createCompany(dto: CreateCompanyDto, userId: Id): ResponseEntity<CompanyDto>
    fun getUserCompanies(userId: Id): ResponseEntity<Collection<CompanyDto>>
    fun getCompanyById(companyId: Id): ResponseEntity<CompanyDto>
}
