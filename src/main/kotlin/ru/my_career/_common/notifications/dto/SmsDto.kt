package ru.my_career._common.notifications.dto

data class SmsDto(
    val phoneNumber: String,
    val message: String,
)
