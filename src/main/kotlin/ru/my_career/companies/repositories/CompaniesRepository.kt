package ru.my_career.companies.repositories

import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ru.my_career._common.database.Id
import ru.my_career._common.users.repositories.UserDao
import ru.my_career.companies.dto.CreateCompanyDto
import ru.my_career.companies.tables.CompaniesTable
import ru.my_career.companies.tables.CompaniesUsersRolesTable

class CompaniesRepository() {
    fun createCompany(dto: CreateCompanyDto): CompanyDao? {
        return transaction {
            CompanyDao.new {
                title = dto.title
            }
        }
    }

    fun getUserCompanies(userId: Id): Collection<CompanyDao>? {
        var companiesIds: Collection<Id> = emptySet()
        transaction {
            companiesIds = CompaniesUsersRolesTable.select { (CompaniesUsersRolesTable.user eq userId) }
                .map { it[CompaniesUsersRolesTable.company].value }
        }

        return getCompaniesByIds(companiesIds)
    }

    fun getCompanyUsers(companyId: Id): Collection<Id>? {
        var companyIds: Collection<Id>? = null
        transaction {
            companyIds = CompaniesUsersRolesTable.select { CompaniesUsersRolesTable.company eq companyId }
                .map { it[CompaniesUsersRolesTable.user].value }
        }

        return companyIds
    }

    fun getCompanyById(companyId: Id): CompanyDao? = transaction { CompanyDao.findById(companyId) }

    private fun getCompaniesByIds(ids: Collection<Id>): Collection<CompanyDao> {
        var companies: Collection<CompanyDao> = emptySet()
        transaction {
            companies = CompaniesTable.select { CompaniesTable.id inList ids }.map { CompanyDao.wrapRow(it) }
        }

        return companies
    }
}
