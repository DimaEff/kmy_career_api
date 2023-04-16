package ru.my_career.roles.tables

import org.jetbrains.exposed.sql.Table

object RolesPermissionsTable: Table() {
    val role = reference("role", RolesTable)
    val permission = reference("permission", PermissionsTable)
}