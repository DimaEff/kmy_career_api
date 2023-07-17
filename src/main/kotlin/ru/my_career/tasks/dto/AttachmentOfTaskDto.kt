package ru.my_career.tasks.dto

import kotlinx.serialization.Serializable
import ru.my_career._common.users.dto.UserDto

@Serializable
data class AttachmentOfTaskDto(
    val title: String,
    val url: String,
    val createdBy: UserDto,
    val createdAt: Int,
)
