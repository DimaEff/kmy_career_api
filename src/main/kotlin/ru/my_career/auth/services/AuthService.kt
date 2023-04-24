package ru.my_career.auth.services

import ru.my_career._common.types.ResponseEntity
import ru.my_career.auth.dto.AuthUserDto
import ru.my_career._common.confirmations.ConfirmationStatus
import ru.my_career._common.database.Id
import ru.my_career._common.users.dto.CreateUserDto

interface AuthService {
    suspend fun login(phoneNumber: String, code: String): ResponseEntity<AuthUserDto>
    fun checkCode(phoneNumber: String, code: String): ResponseEntity<ConfirmationStatus>
    fun register(dto: CreateUserDto): ResponseEntity<AuthUserDto>
    fun authInCompany(userId: Id, companyId: Id): ResponseEntity<AuthUserDto>
}
