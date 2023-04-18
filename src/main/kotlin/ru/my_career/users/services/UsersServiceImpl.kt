package ru.my_career.users.services

import io.ktor.http.*
import ru.my_career._common.database.Id
import ru.my_career._common.types.ResponseEntity
import ru.my_career.users.dto.CreateUserDto
import ru.my_career.users.dto.UserDto
import ru.my_career.users.repositories.UsersRepository
import ru.my_career.users.repositories.toDto

class UsersServiceImpl(
    private val usersRepository: UsersRepository
) : UsersService {
    override fun createUser(dto: CreateUserDto): ResponseEntity<UserDto> {
        val user = usersRepository.createUser(dto) ?: return ResponseEntity(
            HttpStatusCode.InternalServerError,
            errorMessage = "Failed to create a user"
        )

        return ResponseEntity(HttpStatusCode.Created, user.toDto())
    }

    override fun getUserById(id: Id): ResponseEntity<UserDto> {
        val user = usersRepository.getUser(id) ?: return ResponseEntity(
            HttpStatusCode.NotFound,
            errorMessage = "The user not found"
        )
        return ResponseEntity(HttpStatusCode.OK, user.toDto())
    }
}