package ru.my_career.common.notifications.dto

import kotlinx.serialization.Serializable

@Serializable
data class AeroSmsResponseDto(
    val success: Boolean,
    val data: AeroSmsResponseDtoData? = null,
    val message: String? = null
)

@Serializable
data class AeroSmsResponseDtoData(
    val id: Int,
    val from: String,
    val number: String,
    val text: String,
    val status: Int,
    val extendStatus: String,
    val channel: String,
    val dateCreate: Int,
    val dateSend: Int,
)
