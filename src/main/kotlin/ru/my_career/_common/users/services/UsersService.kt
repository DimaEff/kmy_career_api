package ru.my_career._common.users.services

import ru.my_career._common.database.Id
import ru.my_career._common.types.ResponseEntity
import ru.my_career._common.users.dto.CreateUserDto
import ru.my_career._common.users.dto.UserDto

interface UsersService {
    fun createUser(dto: CreateUserDto): ResponseEntity<UserDto>
    fun getUserById(id: Id): ResponseEntity<UserDto>
    fun getUsersByIds(ids: Collection<Id>): ResponseEntity<Collection<UserDto>>
    fun getUserByPhone(phoneNumber: String): ResponseEntity<UserDto>
}
