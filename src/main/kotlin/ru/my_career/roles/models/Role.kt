package ru.my_career.roles.models

import kotlinx.serialization.Serializable
import org.litote.kmongo.newId
import ru.my_career.common.db.MongoId

@Serializable
data class Role(
    val _id: MongoId<Role> = newId(),
    val title: String,
//    val companyId: String,
    val description: String,
    val permissions: Collection<MongoId<Permission>>,
//    val commonTitle: CommonRoleTitle?,
)

@Serializable
data class CreateRoleDto(
    val title: String,
//    val companyId: String,
    val description: String,
    val permissions: Collection<String>,
//    val commonTitle: CommonRoleTitle?,
)
