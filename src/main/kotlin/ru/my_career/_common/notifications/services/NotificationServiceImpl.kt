package ru.my_career._common.notifications.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.my_career._common.notifications.DEFAULT_CODE_SENDER
import ru.my_career._common.confirmations.dto.AeroSmsResponseDto
import ru.my_career._common.notifications.dto.SmsDto
import ru.my_career._common.types.ResponseEntity
import java.util.*

class NotificationServiceImpl(
    private val httpClient: HttpClient
) : NotificationService {
    override suspend fun sendSms(dto: SmsDto): ResponseEntity<AeroSmsResponseDto> {
        val auth = getBasicAuth()
        val response = httpClient.get("https://gate.smsaero.ru/v2/sms/send") {
            headers {
                append("Authorization", auth)
            }
            url {
                parameters.append("number", dto.phoneNumber)
                parameters.append("text", dto.message)
                parameters.append("sign", DEFAULT_CODE_SENDER)
            }
            accept(ContentType.Application.Json)
        }
        val aeroRes = response.body<AeroSmsResponseDto>()

        return ResponseEntity(payload = aeroRes)
    }

    private fun getBasicAuth(): String {
        val base64AuthData =
            Base64.getEncoder().encodeToString("dmitry.fominenkov@gmail.com:L9aresl57pue1O3THkUlMXqyyiUV".toByteArray())
        return "Basic $base64AuthData"
    }
}