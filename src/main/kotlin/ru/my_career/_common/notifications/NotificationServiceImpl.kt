package ru.my_career._common.notifications

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import ru.my_career._common.notifications.dto.AeroSmsResponseDto
import ru.my_career._common.notifications.dto.SmsDto
import java.util.*

val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
}

class NotificationServiceImpl : NotificationService {
    override suspend fun sendSms(dto: SmsDto): AeroSmsResponseDto {
        val authBase64 =
            Base64.getEncoder().encodeToString("dmitry.fominenkov@gmail.com:L9aresl57pue1O3THkUlMXqyyiUV".toByteArray())
        val response = client.get("https://gate.smsaero.ru/v2/sms/send") {
            headers {
                append("Authorization", "Basic $authBase64")
            }
            url {
                parameters.append("number", dto.phone)
                parameters.append("text", getTemplateConfirmationCodeMessage(dto.code))
                parameters.append("sign", DEFAULT_CODE_SENDER)
            }
            accept(ContentType.Application.Json)
        }

        return response.body<AeroSmsResponseDto>()
    }
}