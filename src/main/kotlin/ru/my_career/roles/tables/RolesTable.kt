package ru.my_career.roles.tables

import ru.my_career.common.database.DefaultTable
import ru.my_career.common.database.VARCHAR_MID
import ru.my_career.common.database.VARCHAR_SHORT
import ru.my_career.common.database.custom_db_types.enumType
import ru.my_career.roles.CommonRoleTitle

object RolesTable : DefaultTable() {
    val title = varchar("title", VARCHAR_SHORT).uniqueIndex()
    val description = varchar("description", VARCHAR_MID)
    val commonRoleTitle = enumType("common_role_title", CommonRoleTitle)
}