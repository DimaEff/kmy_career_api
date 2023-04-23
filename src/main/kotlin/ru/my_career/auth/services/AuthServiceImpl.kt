package ru.my_career.auth.services

import ru.my_career._common.types.ResponseEntity
import ru.my_career.auth.dto.AuthUserDto
import ru.my_career._common.confirmations.ConfirmationStatus
import ru.my_career._common.confirmations.services.ConfirmationsService
import ru.my_career._common.users.dto.CreateUserDto
import ru.my_career._common.users.services.UsersService

class AuthServiceImpl(
    private val confirmationsService: ConfirmationsService,
    private val usersService: UsersService
) : AuthService {
    override suspend fun login(phoneNumber: String, code: String): ResponseEntity<AuthUserDto> {
        val confirmationRes = confirmationsService.checkCode(phoneNumber, code)

        return if (confirmationRes.payload === ConfirmationStatus.CONFIRMED) {
            val userRes = usersService.getUserByPhone(phoneNumber)
            ResponseEntity(userRes.statusCode, AuthUserDto(confirmationRes.payload, userRes.payload))
        } else {
            ResponseEntity(confirmationRes.statusCode, AuthUserDto(confirmationRes.payload))
        }
    }

    override fun checkCode(phoneNumber: String, code: String): ResponseEntity<ConfirmationStatus> =
        confirmationsService.checkCode(phoneNumber, code)

    override fun register(dto: CreateUserDto): ResponseEntity<AuthUserDto> {
        val confirmationRes = confirmationsService.isConfirmed(dto.phoneNumber)
        return if (confirmationRes.payload === ConfirmationStatus.CONFIRMED) {
            val userRes = usersService.createUser(dto)
            ResponseEntity(userRes.statusCode, AuthUserDto(confirmationRes.payload, userRes.payload))
        } else {
            ResponseEntity(confirmationRes.statusCode, AuthUserDto(confirmationRes.payload))
        }
    }
}