package ru.my_career.users.services

import ru.my_career._common.database.Id
import ru.my_career._common.types.ResponseEntity
import ru.my_career.users.dto.CreateUserDto
import ru.my_career.users.dto.UserDto

interface UsersService {
    fun createUser(dto: CreateUserDto): ResponseEntity<UserDto>
    fun getUserById(id: Id): ResponseEntity<UserDto>
}
