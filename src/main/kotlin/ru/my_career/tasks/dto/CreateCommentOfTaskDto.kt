package ru.my_career.tasks.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateCommentOfTaskDto(
    val message: String,
    val taskId: Int,
)
