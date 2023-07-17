package ru.my_career.tasks.services

import io.ktor.http.*
import ru.my_career._common.types.ResponseEntity
import ru.my_career._common.users.repositories.UsersRepository
import ru.my_career.companies.repositories.CompaniesRepository
import ru.my_career.tasks.dto.*
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
        val tasks = tasksRepository.getAll() ?: return ResponseEntity(
            HttpStatusCode.BadRequest,
            errorMessage = "Error while getting all tasks"
        )
        return ResponseEntity(payload = tasks.map { it.toDto() })
    }

    override fun getCompanyTasks(companyId: Int): ResponseEntity<Collection<TaskDto>> {
        val tasks =
            tasksRepository.getAllByCompany(companyId) ?: return ResponseEntity(
                HttpStatusCode.BadRequest,
                errorMessage = "Some error"
            )
        return ResponseEntity(payload = tasks.map { it.toDto() })
    }

    override fun getById(taskId: Int): ResponseEntity<TaskDto> {
        val task = tasksRepository.getById(taskId) ?: return ResponseEntity(
            HttpStatusCode.BadRequest,
            errorMessage = "Some error"
        )
        return ResponseEntity(payload = task.toDto())
    }

    override fun getTasksAssignedTo(userId: Int): ResponseEntity<Collection<TaskDto>> {
        val tasks = tasksRepository.getTasksByAssignedTo(userId) ?: return ResponseEntity(
            HttpStatusCode.BadRequest,
            errorMessage = "Some error"
        )
        return ResponseEntity(payload = tasks.map { it.toDto() })
    }

    override fun getNotAssignedTasks(): ResponseEntity<Collection<TaskDto>> {
        val tasks = tasksRepository.getTasksByAssignedTo(null) ?: return ResponseEntity(
            HttpStatusCode.BadRequest,
            errorMessage = "Some error"
        )
        return ResponseEntity(payload = tasks.map { it.toDto() })
    }

    override fun getTasksByCreated(userId: Int): ResponseEntity<Collection<TaskDto>> {
        val tasks = tasksRepository.getTasksByCreated(userId) ?: return ResponseEntity(
            HttpStatusCode.BadRequest,
            errorMessage = "Some error"
        )
        return ResponseEntity(payload = tasks.map { it.toDto() })
    }

    override fun updateTask(dto: UpdateTaskDto): ResponseEntity<TaskDto> {
        val user = dto.assignedTo?.let { usersRepository.getUser(it) }
        val task = tasksRepository.updateTask(dto, user) ?: return ResponseEntity(
            HttpStatusCode.BadRequest,
            errorMessage = "Some error"
        )
        return ResponseEntity(payload = task.toDto())
    }

    override fun createCommentOfTask(dto: CreateCommentOfTaskDto, userId: Int): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun getCommentsOfTask(taskId: Int): ResponseEntity<Collection<CommentOfTaskDto>> {
        TODO("Not yet implemented")
    }
}