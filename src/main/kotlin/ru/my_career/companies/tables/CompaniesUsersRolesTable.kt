package ru.my_career.companies.tables

import org.jetbrains.exposed.sql.Table
import ru.my_career.roles.tables.RolesTable
import ru.my_career.users.tables.UsersTable

object CompaniesUsersRolesTable: Table() {
    val company = reference("company", CompaniesTable)
    val user = reference("name", UsersTable)
    val role = reference("role", RolesTable)
}
