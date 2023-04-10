package ru.my_career.companies.models

import kotlinx.serialization.Serializable
import org.litote.kmongo.newId
import ru.my_career.common.db.MongoId

@Serializable
data class Company(
    val _id: MongoId<Company> = newId(),
    val title: String,
)

@Serializable
data class CreateCompanyDto(
    val title: String,
    val userId: String,
)
