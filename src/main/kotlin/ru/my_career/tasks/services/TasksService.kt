package ru.my_career.tasks.services

import ru.my_career._common.types.ResponseEntity
import ru.my_career.tasks.dto.*

interface TasksService {
    // tasks
    fun createTask(dto: CreateTaskDto, companyId: Int, userId: Int): ResponseEntity<TaskDto>
    fun getAll(): ResponseEntity<Collection<TaskDto>>
    fun getOne(taskId: Int): ResponseEntity<TaskDto>
    fun getAssignedToTasks(userId: Int): ResponseEntity<Collection<TaskDto>>
    fun getCreatedByTasks(userId: Int): ResponseEntity<Collection<TaskDto>>

    // comments
    fun createCommentOfTask(dto: CreateCommentOfTaskDto, userId: Int): ResponseEntity<String>
    fun getCommentsOfTask(taskId: Int): ResponseEntity<Collection<CommentOfTaskDto>>

    // attachments
    // fun getAttachmentsOfTask(taskId: Int): ResponseEntity<Collection<AttachmentOfTaskDto>>
}