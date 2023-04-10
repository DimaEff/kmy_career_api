package ru.my_career.users.models

import kotlinx.serialization.Serializable
import org.litote.kmongo.newId
import ru.my_career.common.db.MongoId

@Serializable
data class User(
    val _id: MongoId<User> = newId(),
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
    val _isBlocked: Boolean = false,
    val _isActive: Boolean = true
)


@Serializable
data class CreateUserDto(
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
)
