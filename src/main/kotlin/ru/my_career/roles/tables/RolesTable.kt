package ru.my_career.roles.tables

import org.jetbrains.exposed.sql.ReferenceOption
import ru.my_career._common.database.DefaultTable
import ru.my_career._common.database.VARCHAR_MID
import ru.my_career._common.database.VARCHAR_SHORT
import ru.my_career._common.database.custom_db_types.enumType
import ru.my_career.companies.tables.CompaniesTable
import ru.my_career.roles.CommonRoleTitle

// https://github.com/DimaEff/my_career_api1/wiki/1.-%D0%9A%D0%BE%D0%BC%D0%BF%D0%B0%D0%BD%D0%B8%D0%B8.-%D0%A1%D0%BE%D1%82%D1%80%D1%83%D0%B4%D0%BD%D0%B8%D0%BA%D0%B8.-%D0%A0%D0%BE%D0%BB%D0%B8#%D1%81%D1%85%D0%B5%D0%BC%D0%B0-permission
object RolesTable : DefaultTable() {
    val title = varchar("title", VARCHAR_SHORT)
    val description = varchar("description", VARCHAR_MID)
    val commonRoleTitle = enumType("common_role_title", CommonRoleTitle).nullable()
    val company = reference("company", CompaniesTable, onDelete = ReferenceOption.CASCADE)
}