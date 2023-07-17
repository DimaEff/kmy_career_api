package ru.my_career.tasks.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import ru.my_career._common.database.VARCHAR_MID
import ru.my_career._common.database.VARCHAR_SHORT
import ru.my_career._common.database.custom_db_types.enumType
import ru.my_career._common.users.tables.UsersTable
import ru.my_career.companies.tables.CompaniesTable
import ru.my_career.tasks.TaskStatus

object TasksTable : IntIdTable() {
    val title = varchar("title", VARCHAR_SHORT)
    val description = varchar("description", VARCHAR_MID)
    val company = reference("company", CompaniesTable, onDelete = ReferenceOption.CASCADE)
    val createdBy = reference("createdBy", UsersTable, onDelete = ReferenceOption.CASCADE)
    val assignedTo = reference("assignedTo", UsersTable, onDelete = ReferenceOption.CASCADE).nullable().default(null)
    val createdAt = datetime("createdAt").defaultExpression(CurrentDateTime)
    val dueDate = datetime("dueDate").nullable()
    val status = enumType("status", TaskStatus).default(TaskStatus.TODO)
}