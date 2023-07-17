package ru.my_career.tasks.dto

import kotlinx.serialization.Serializable
import ru.my_career._common.users.dto.UserDto

@Serializable
data class CommentOfTaskDto(
    val message: String,
    val createdBy: UserDto,
    val createdAt: Long,
)
