package ru.my_career.tasks.services

import io.ktor.http.*
import ru.my_career._common.types.ResponseEntity
import ru.my_career._common.users.repositories.UsersRepository
import ru.my_career._common.users.services.UsersService
import ru.my_career.companies.repositories.CompaniesRepository
import ru.my_career.tasks.dto.CommentOfTaskDto
import ru.my_career.tasks.dto.CreateCommentOfTaskDto
import ru.my_career.tasks.dto.CreateTaskDto
import ru.my_career.tasks.dto.TaskDto
import ru.my_career.tasks.repositories.TasksRepository
import ru.my_career.tasks.repositories.toDto

class TasksServiceImpl(
    private val tasksRepository: TasksRepository,
    private val companiesRepository: CompaniesRepository,
    private val usersRepository: UsersRepository,
) : TasksService {
    override fun createTask(dto: CreateTaskDto, companyId: Int, userId: Int): ResponseEntity<TaskDto> {
        val company = companiesRepository.getCompanyById(companyId)
        val createdBy = usersRepository.getUser(userId)
        val assignedTo = dto.assignedToId?.let { usersRepository.getUser(it) }

        if (company == null || createdBy == null) {
            return ResponseEntity(HttpStatusCode.NotFound, errorMessage = "The company or the user not found")
        }

        val task = tasksRepository.createTask(dto, company, createdBy, assignedTo) ?: return ResponseEntity(
            HttpStatusCode.BadRequest, errorMessage = "An error while creating a task."
        )
        return ResponseEntity(payload = task.toDto())
    }

    override fun getAll(): ResponseEntity<Collection<TaskDto>> {
        TODO("Not yet implemented")
    }

    override fun getOne(taskId: Int): ResponseEntity<TaskDto> {
        TODO("Not yet implemented")
    }

    override fun getAssignedToTasks(userId: Int): ResponseEntity<Collection<TaskDto>> {
        TODO("Not yet implemented")
    }

    override fun getCreatedByTasks(userId: Int): ResponseEntity<Collection<TaskDto>> {
        TODO("Not yet implemented")
    }

    override fun createCommentOfTask(dto: CreateCommentOfTaskDto, userId: Int): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun getCommentsOfTask(taskId: Int): ResponseEntity<Collection<CommentOfTaskDto>> {
        TODO("Not yet implemented")
    }
}