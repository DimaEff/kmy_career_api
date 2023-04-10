package ru.my_career.companies.models

import kotlinx.serialization.Serializable
import org.litote.kmongo.newId
import ru.my_career.common.db.MongoId
import ru.my_career.roles.models.Role

@Serializable
data class Company(
    val _id: MongoId<Company> = newId(),
    val title: String,
    val roles: Collection<MongoId<Role>>,
)

@Serializable
data class CreateCompanyDto(
    val title: String,
    val userId: String,
)
