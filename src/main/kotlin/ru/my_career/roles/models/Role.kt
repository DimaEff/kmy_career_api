package ru.my_career.roles.models

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.litote.kmongo.newId
import ru.my_career.common.db.MongoId
import ru.my_career.common.serializers.ObjectIdSerializer
import ru.my_career.companies.models.Company
import ru.my_career.roles.CommonRoleTitle

@Serializable
data class Role(
    val _id: MongoId<Role> = newId(),
    val title: String,
    val description: String,
    @Serializable(with = ObjectIdSerializer::class)
    val companyId: ObjectId,
    val permissions: Collection<String>,
    val commonTitle: CommonRoleTitle? = null,
)

@Serializable
data class CreateRoleDto(
    val title: String,
    val description: String,
    val companyId: String,
    val permissions: Collection<String>,
    val commonTitle: String?,
)
