package ru.my_career.users.models

import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class User(
    val _id: Id<User> = newId(),
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
    var _isActive: Boolean = true
)


@Serializable
data class CreateUserDto(
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
)
