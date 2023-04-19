package ru.my_career._common.confirmations.services

import io.ktor.http.*
import ru.my_career._common.confirmations.ConfirmationStatus
import ru.my_career._common.confirmations.dto.AddCheckConfirmationDto
import ru.my_career._common.confirmations.repositories.ConfirmationsRepository
import ru.my_career._common.notifications.dto.SmsDto
import ru.my_career._common.notifications.services.NotificationService
import ru.my_career._common.types.ResponseEntity

class ConfirmationsServiceImpl(
    private val confirmationsRepository: ConfirmationsRepository,
    private val notificationService: NotificationService
) : ConfirmationsService {
    override suspend fun addConfirmation(phoneNumber: String): ResponseEntity<String> {
        val code = getRandomCode()
        val aeroRes = notificationService.sendSms(SmsDto(phoneNumber, getTemplateConfirmationCodeMessage(code)))
        if (aeroRes.payload?.success != true) {
            return ResponseEntity(aeroRes.statusCode, errorMessage = aeroRes.errorMessage)
        }

        confirmationsRepository.addConfirmation(AddCheckConfirmationDto(phoneNumber, code)) ?: return ResponseEntity(
            HttpStatusCode.InternalServerError,
            "Error while adding a confirmation"
        )

        return ResponseEntity(HttpStatusCode.Created, "Success added the confirmation")
    }

    override fun checkConfirmation(dto: AddCheckConfirmationDto): ResponseEntity<ConfirmationStatus> {
        val confirmation = confirmationsRepository.getConfirmationBySubject(dto.confirmationSubject)
        return when (confirmation?.code) {
            null -> ResponseEntity(HttpStatusCode.NotFound, ConfirmationStatus.INVALID_SUBJECT)
            dto.code -> {
                confirmationsRepository.setConfirmed(confirmation)
                ResponseEntity(payload = ConfirmationStatus.SUCCESS)
            }
            else -> ResponseEntity(payload = ConfirmationStatus.INVALID_CODE)
        }
    }

    private fun getRandomCode(): String = (1000..9999).random().toString()
    private fun getTemplateConfirmationCodeMessage(code: String) = "Ваш+код+$code"
}