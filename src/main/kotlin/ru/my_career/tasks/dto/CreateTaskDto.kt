package ru.my_career.tasks.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskDto(
    val title: String,
    val description: String,
    val assignedToId: Int?,
    val dueDate: Long?,
)