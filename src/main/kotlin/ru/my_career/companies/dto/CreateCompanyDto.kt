package ru.my_career.companies.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateCompanyDto(
    val title: String,
)
