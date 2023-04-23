package ru.my_career._common.confirmations.tables

import ru.my_career._common.database.DefaultTable
import ru.my_career._common.database.VARCHAR_CONFIRMATION_CODE
import ru.my_career._common.database.VARCHAR_SHORT

object ConfirmationsTable: DefaultTable() {
    val confirmationSubject = varchar("confirmation_subject", VARCHAR_SHORT).uniqueIndex()
    val code = varchar("code", VARCHAR_CONFIRMATION_CODE).nullable()
    val isConfirmed = bool("is_confirmed").default(false)
}
