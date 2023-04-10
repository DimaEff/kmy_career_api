package ru.my_career.roles.models

import kotlinx.serialization.Serializable
import org.litote.kmongo.newId
import ru.my_career.common.db.MongoId
import ru.my_career.companies.models.Company
import ru.my_career.roles.CommonRoleTitle

@Serializable
data class Role(
    val _id: MongoId<Role> = newId(),
    val title: String,
    val description: String,
    val permissions: Collection<MongoId<Permission>>,
    val commonTitle: CommonRoleTitle? = null,
)

@Serializable
data class CreateRoleDto(
    val title: String,
    val description: String,
    val permissions: Collection<String>,
    val commonTitle: String?,
)
