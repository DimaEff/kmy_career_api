package ru.my_career.tasks.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import ru.my_career._common.database.VARCHAR_LONG
import ru.my_career._common.users.tables.UsersTable

object CommentsOfTasksTable : IntIdTable() {
    val message = varchar("message", VARCHAR_LONG)
    val createdBy = reference("createdBy", UsersTable, onDelete = ReferenceOption.CASCADE)
    val createdAt = datetime("createdAt").defaultExpression(CurrentDateTime)
    val task = reference("task", TasksTable, onDelete = ReferenceOption.CASCADE)
}
