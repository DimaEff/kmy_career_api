package ru.my_career.companies.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import ru.my_career.roles.tables.RolesTable
import ru.my_career._common.users.tables.UsersTable

object CompaniesUsersRolesTable: Table() {
    val company = reference("company", CompaniesTable, onDelete = ReferenceOption.CASCADE)
    val user = reference("name", UsersTable, onDelete = ReferenceOption.CASCADE)
    val role = reference("role", RolesTable, onDelete = ReferenceOption.CASCADE)
}
