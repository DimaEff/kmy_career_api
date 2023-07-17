package ru.my_career.tasks.dto

import kotlinx.serialization.Serializable
import ru.my_career._common.users.dto.UserDto
import ru.my_career.tasks.TaskStatus

@Serializable
data class TaskDto(
    val id: Int,
    val title: String,
    val description: String,
    val createdBy: UserDto,
    val assignedTo: UserDto? = null,
    val createdAt: Long,
    val dueDate: Long? = null,
    val status: TaskStatus,
)
