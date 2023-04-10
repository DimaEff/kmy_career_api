package ru.my_career.companies.models

import kotlinx.serialization.Serializable
import org.litote.kmongo.newId
import ru.my_career.common.db.MongoId
import ru.my_career.roles.models.Role
import ru.my_career.users.models.User

@Serializable
data class CompanyUserRole(
    val _id: MongoId<CompanyUserRole> = newId(),
    val companyId: MongoId<Company>,
    val userId: MongoId<User>,
    val roleId: MongoId<Role>
)
