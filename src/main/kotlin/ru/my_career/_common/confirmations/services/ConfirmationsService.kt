package ru.my_career._common.confirmations.services

import ru.my_career._common.confirmations.ConfirmationStatus
import ru.my_career._common.types.ResponseEntity

interface ConfirmationsService {
    suspend fun addConfirmation(confirmationSubject: String): ResponseEntity<String>
    fun checkCode(confirmationSubject: String, code: String): ResponseEntity<ConfirmationStatus>
    fun isConfirmed(confirmationSubject: String): ResponseEntity<ConfirmationStatus>
}
