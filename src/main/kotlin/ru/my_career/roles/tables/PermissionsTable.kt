package ru.my_career.roles.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import ru.my_career.common.database.*

object PermissionsTable : IntIdTable() {
    val title = varchar("title", VARCHAR_SHORT).uniqueIndex()
    val description = varchar("description", VARCHAR_MID)
}
