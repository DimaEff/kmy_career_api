package ru.my_career.users.services

import ru.my_career.common.db.MongoId
import ru.my_career.users.models.CreateUserDto
import ru.my_career.users.models.User

interface UsersService {
    suspend fun createUser(dto: CreateUserDto): User?
    suspend fun getUserById(userId: String): User?
}