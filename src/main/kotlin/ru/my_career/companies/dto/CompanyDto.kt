package ru.my_career.companies.dto

import kotlinx.serialization.Serializable
import ru.my_career._common.database.Id

@Serializable
data class CompanyDto(
    val id: Id,
    val title: String,
)
