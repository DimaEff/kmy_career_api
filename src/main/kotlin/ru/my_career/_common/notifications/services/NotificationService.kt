package ru.my_career._common.notifications.services

import ru.my_career._common.notifications.dto.AeroSmsResponseDto
import ru.my_career._common.notifications.dto.SmsDto
import ru.my_career._common.types.ResponseEntity

interface NotificationService {
    suspend fun sendSms(dto: SmsDto): ResponseEntity<AeroSmsResponseDto>
}