package ru.my_career.roles.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import ru.my_career._common.database.*

// https://github.com/DimaEff/my_career_api1/wiki/1.-%D0%9A%D0%BE%D0%BC%D0%BF%D0%B0%D0%BD%D0%B8%D0%B8.-%D0%A1%D0%BE%D1%82%D1%80%D1%83%D0%B4%D0%BD%D0%B8%D0%BA%D0%B8.-%D0%A0%D0%BE%D0%BB%D0%B8#%D1%81%D1%85%D0%B5%D0%BC%D0%B0-permission
object PermissionsTable : IntIdTable() {
    val title = varchar("title", VARCHAR_SHORT).uniqueIndex()
    val description = varchar("description", VARCHAR_MID)
}
