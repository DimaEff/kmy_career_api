package ru.my_career.tasks.repositories

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.my_career._common.database.Id
import ru.my_career._common.users.repositories.UserDao
import ru.my_career._common.users.repositories.toDto
import ru.my_career._common.utils.localDateTimeToMilli
import ru.my_career.tasks.dto.CommentOfTaskDto
import ru.my_career.tasks.tables.CommentsOfTasksTable

class CommentsOfTasksRepository {
    private val logger = LoggerFactory.getLogger(CommentsOfTasksRepository::class.java)
}

class CommentOfTaskDao(id: EntityID<Id>) : IntEntity(id) {
    companion object : IntEntityClass<CommentOfTaskDao>(CommentsOfTasksTable)

    var message by CommentsOfTasksTable.message
    var createdBy by UserDao referencedOn CommentsOfTasksTable.createdBy
    var createdAt by CommentsOfTasksTable.createdAt
    var task by CommentOfTaskDao referencedOn CommentsOfTasksTable.task
}

fun CommentOfTaskDao.toDto() = transaction {
    CommentOfTaskDto(
        message = this@toDto.message,
        createdBy = this@toDto.createdBy.toDto(),
        createdAt = localDateTimeToMilli(this@toDto.createdAt)
    )
}
