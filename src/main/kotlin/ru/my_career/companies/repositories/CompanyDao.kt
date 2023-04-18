package ru.my_career.companies.repositories

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.my_career._common.database.Id
import ru.my_career.companies.dto.CompanyDto
import ru.my_career.companies.tables.CompaniesTable
import ru.my_career.roles.repositories.RoleDao
import ru.my_career.roles.tables.RolesTable

class CompanyDao(id: EntityID<Id>): IntEntity(id) {
    companion object: IntEntityClass<CompanyDao>(CompaniesTable)
    var title by CompaniesTable.title
    val roles by RoleDao referrersOn RolesTable.company
}

fun CompanyDao.toDto() = CompanyDto(this.id.value, this.title)
