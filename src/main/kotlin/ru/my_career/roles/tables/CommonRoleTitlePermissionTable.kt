package ru.my_career.roles.tables

import org.jetbrains.exposed.sql.ReferenceOption
import ru.my_career._common.database.DefaultTable
import ru.my_career._common.database.custom_db_types.enumType
import ru.my_career.roles.CommonRoleTitle

object CommonRoleTitlePermissionTable: DefaultTable() {
    val commonRoleTitle = enumType("common_role_title", CommonRoleTitle)
    val permission = reference("permission", PermissionsTable, onDelete = ReferenceOption.CASCADE)
}