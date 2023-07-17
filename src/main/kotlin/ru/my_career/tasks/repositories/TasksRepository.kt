package ru.my_career.tasks.repositories

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.my_career._common.database.Id
import ru.my_career._common.users.repositories.UserDao
import ru.my_career._common.users.repositories.toDto
import ru.my_career._common.utils.localDateTimeToMilli
import ru.my_career._common.utils.milliToLocalDateTime
import ru.my_career.companies.repositories.CompanyDao
import ru.my_career.tasks.dto.CreateTaskDto
import ru.my_career.tasks.dto.TaskDto
import ru.my_career.tasks.tables.CommentsOfTasksTable
import ru.my_career.tasks.tables.TasksTable

class TasksRepository {
    private val logger = LoggerFactory.getLogger(TasksRepository::class.java)

    fun createTask(
        dto: CreateTaskDto,
        companyDao: CompanyDao,
        createdByDao: UserDao,
        assignedToDao: UserDao?
    ): TaskDao? = transaction {
        TaskDao.new {
            title = dto.title
            description = dto.description
            company = companyDao
            createdBy = createdByDao
            assignedTo = assignedToDao
            dueDate = dto.dueDate?.let { milliToLocalDateTime(it) }
            // status = TaskStatus.TODO is a default value in the TasksTable
        }
    }

    fun getAll(): Collection<TaskDao>?  = transaction {
        val tasks = mutableListOf<TaskDao>()
        TaskDao.all().toCollection(tasks)
    }

    fun getAllByCompany(companyId: Int): Collection<TaskDao>? {
        val tasks = mutableListOf<TaskDao>()
        transaction {
            TasksTable.select { TasksTable.company eq companyId }.map { TaskDao.wrapRow(it) }.toCollection(tasks)
        }
        return tasks
    }
}

class TaskDao(id: EntityID<Id>) : IntEntity(id) {
    companion object : IntEntityClass<TaskDao>(TasksTable)

    var title by TasksTable.title
    var description by TasksTable.description
    var company by CompanyDao referencedOn TasksTable.company
    var createdBy by UserDao referencedOn TasksTable.createdBy
    var assignedTo by UserDao optionalReferencedOn TasksTable.assignedTo
    var createdAt by TasksTable.createdAt
    var dueDate by TasksTable.dueDate
    var status by TasksTable.status
    val comments by CommentOfTaskDao referrersOn CommentsOfTasksTable.task
//    val attachments by
}

fun TaskDao.toDto() = transaction {
    TaskDto(
        title = this@toDto.title,
        description = this@toDto.description,
        createdBy = this@toDto.createdBy.toDto(),
        assignedTo = this@toDto.assignedTo?.toDto(),
        createdAt = localDateTimeToMilli(this@toDto.createdAt),
        dueDate = this@toDto.dueDate?.let { localDateTimeToMilli(it) },
        status = this@toDto.status,
    )
}
