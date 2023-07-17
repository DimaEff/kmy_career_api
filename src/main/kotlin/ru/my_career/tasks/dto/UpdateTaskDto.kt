package ru.my_career.tasks.dto

import kotlinx.serialization.Serializable
import ru.my_career.tasks.DEFAULT_NOT_SET_VALUE_OF_ASSIGNED_TO
import ru.my_career.tasks.DEFAULT_NOT_SET_VALUE_OF_DUE_DATE
import ru.my_career.tasks.TaskStatus

@Serializable
data class UpdateTaskDto(
    val id: Int,
    val title: String? = null,
    val description: String? = null,
    val assignedTo: Int? = DEFAULT_NOT_SET_VALUE_OF_ASSIGNED_TO,
    val dueDate: Long? = DEFAULT_NOT_SET_VALUE_OF_DUE_DATE,
    val status: TaskStatus? = null,
)
