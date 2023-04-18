package ru.my_career.companies.tables

import ru.my_career._common.database.DefaultTable
import ru.my_career._common.database.VARCHAR_SHORT

object CompaniesTable: DefaultTable() {
    val title = varchar("title", VARCHAR_SHORT)
}
