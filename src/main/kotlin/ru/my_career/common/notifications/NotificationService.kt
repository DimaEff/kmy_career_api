package ru.my_career.common.notifications

import ru.my_career.common.notifications.dto.AeroSmsResponseDto
import ru.my_career.common.notifications.dto.SmsDto

interface NotificationService {
    suspend fun sendSms(dto: SmsDto): AeroSmsResponseDto
}