package ru.my_career.tasks.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import ru.my_career._common.database.VARCHAR_MID
import ru.my_career._common.database.VARCHAR_SHORT
import ru.my_career._common.users.tables.UsersTable
import ru.my_career.companies.tables.CompaniesTable

object AttachmentsOfTasksTable: IntIdTable() {
    val title = varchar("title", VARCHAR_SHORT)
    val url = varchar("url", VARCHAR_MID)
    val company = reference("company", CompaniesTable, onDelete = ReferenceOption.CASCADE)
    val createdBy = reference("createdBy", UsersTable, onDelete = ReferenceOption.CASCADE)
    val createdAt = datetime("createdAt").defaultExpression(CurrentDateTime)
    val task = reference("task", TasksTable, onDelete = ReferenceOption.CASCADE)
}