package ru.my_career.roles.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object RolesPermissionsTable: Table() {
    val role = reference("role", RolesTable, onDelete = ReferenceOption.CASCADE)
    val permission = reference("permission", PermissionsTable, onDelete = ReferenceOption.CASCADE)
}