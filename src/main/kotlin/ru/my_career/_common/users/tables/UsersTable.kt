package ru.my_career._common.users.tables

import ru.my_career._common.database.DefaultTable
import ru.my_career._common.database.VARCHAR_PHONE_NUMBER
import ru.my_career._common.database.VARCHAR_SHORT

object UsersTable: DefaultTable() {
    val firstname = varchar("firstname", VARCHAR_SHORT)
    val surname = varchar("surname", VARCHAR_SHORT)
    val phoneNumber = varchar("phone_number", VARCHAR_PHONE_NUMBER).uniqueIndex()
    val email = varchar("email", VARCHAR_SHORT).uniqueIndex()
}