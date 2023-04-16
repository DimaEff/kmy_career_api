package ru.my_career._common.notifications.dto

import kotlinx.serialization.Serializable

@Serializable
data class SmsDto(
    val phone: String,
    val code: String,
)
