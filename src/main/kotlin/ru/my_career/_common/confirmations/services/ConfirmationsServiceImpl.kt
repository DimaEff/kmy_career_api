package ru.my_career._common.confirmations.services

import io.ktor.http.*
import ru.my_career._common.confirmations.ConfirmationStatus
import ru.my_career._common.confirmations.repositories.ConfirmationsRepository
import ru.my_career._common.notifications.services.NotificationService
import ru.my_career._common.types.ResponseEntity

class ConfirmationsServiceImpl(
    private val confirmationsRepository: ConfirmationsRepository,
    private val notificationService: NotificationService
) : ConfirmationsService {
    override suspend fun addConfirmation(confirmationSubject: String): ResponseEntity<String> {
        val code = getRandomCode()
//        todo: in the development mode we shouldn`t use aeroSms
//        val aeroRes = notificationService.sendSms(SmsDto(confirmationSubject, getTemplateConfirmationCodeMessage(code)))
//        if (aeroRes.payload?.success != true) {
//            return ResponseEntity(aeroRes.statusCode, errorMessage = aeroRes.errorMessage)
//        }

        val confirmation = confirmationsRepository.getConfirmationBySubject(confirmationSubject)

        if (confirmation == null) {
            confirmationsRepository.createConfirmation(confirmationSubject, code)
                ?: return ResponseEntity<String>(
                    HttpStatusCode.InternalServerError,
                    errorMessage = "Error while adding a confirmation"
                )
        } else {
            confirmationsRepository.setCode(confirmation, code)
        }


        return ResponseEntity(HttpStatusCode.Created, "Success added the confirmation")
    }

    override fun checkCode(confirmationSubject: String, code: String): ResponseEntity<ConfirmationStatus> {
        val confirmation = confirmationsRepository.getConfirmationBySubject(confirmationSubject)
        return when (confirmation?.code) {
            null -> ResponseEntity(HttpStatusCode.NotFound, ConfirmationStatus.INVALID_SUBJECT)
            code -> {
                confirmationsRepository.setConfirmedAndClearCode(confirmation)
                ResponseEntity(payload = ConfirmationStatus.CONFIRMED)
            }

            else -> ResponseEntity(payload = ConfirmationStatus.INVALID_CODE)
        }
    }

    override fun isConfirmed(confirmationSubject: String): ResponseEntity<ConfirmationStatus> {
        val confirmation = confirmationsRepository.getConfirmationBySubject(confirmationSubject)
        return when(confirmation?.isConfirmed) {
            null -> ResponseEntity(payload = ConfirmationStatus.INVALID_SUBJECT)
            true -> ResponseEntity(payload = ConfirmationStatus.CONFIRMED)
            false -> ResponseEntity(payload = ConfirmationStatus.NOT_CONFIRMED)
        }
    }

    private fun getRandomCode(): String = (1000..9999).random().toString()
    private fun getTemplateConfirmationCodeMessage(code: String) = "Ваш+код+$code"
}