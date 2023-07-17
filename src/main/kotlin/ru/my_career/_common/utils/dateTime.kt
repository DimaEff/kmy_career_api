package ru.my_career._common.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

fun localDateTimeToMilli(dateTime: LocalDateTime): Long = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
fun milliToLocalDateTime(dateTime: Long): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.systemDefault())
