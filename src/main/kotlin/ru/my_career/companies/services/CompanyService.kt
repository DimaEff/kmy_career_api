package ru.my_career.companies.services

import ru.my_career.auth.models.JwtInfo
import ru.my_career.companies.models.Company
import ru.my_career.companies.models.CreateCompanyDto

interface CompanyService {
    suspend fun createCompany(dto: CreateCompanyDto, jwtInfo: JwtInfo): Company?
    suspend fun getCompanyById(companyId: String): Company?
}