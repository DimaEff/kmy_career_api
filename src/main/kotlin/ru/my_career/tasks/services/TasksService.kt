package ru.my_career.tasks.services

import ru.my_career._common.types.ResponseEntity
import ru.my_career.tasks.dto.*

interface TasksService {
    // tasks
    fun createTask(dto: CreateTaskDto, companyId: Int, userId: Int): ResponseEntity<TaskDto>
    fun getAll(): ResponseEntity<Collection<TaskDto>>
    fun getCompanyTasks(companyId: Int): ResponseEntity<Collection<TaskDto>>
    fun getById(taskId: Int): ResponseEntity<TaskDto>
    fun getTasksAssignedTo(userId: Int): ResponseEntity<Collection<TaskDto>>
    fun getNotAssignedTasks(): ResponseEntity<Collection<TaskDto>>
    fun getTasksByCreated(userId: Int): ResponseEntity<Collection<TaskDto>>
    fun updateTask(dto: UpdateTaskDto): ResponseEntity<TaskDto>

    // comments
    fun createCommentOfTask(dto: CreateCommentOfTaskDto, userId: Int): ResponseEntity<String>
    fun getCommentsOfTask(taskId: Int): ResponseEntity<Collection<CommentOfTaskDto>>

    // attachments
    // fun getAttachmentsOfTask(taskId: Int): ResponseEntity<Collection<AttachmentOfTaskDto>>
}