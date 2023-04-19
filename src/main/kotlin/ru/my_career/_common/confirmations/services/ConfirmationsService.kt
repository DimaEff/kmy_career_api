package ru.my_career._common.confirmations.services

import ru.my_career._common.confirmations.ConfirmationStatus
import ru.my_career._common.confirmations.dto.AddCheckConfirmationDto
import ru.my_career._common.types.ResponseEntity

interface ConfirmationsService {
    suspend fun addConfirmation(phoneNumber: String): ResponseEntity<String>
    fun checkConfirmation(dto: AddCheckConfirmationDto): ResponseEntity<ConfirmationStatus>
}
