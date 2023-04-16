package ru.my_career._common.types

import io.ktor.http.*

data class ResponseEntity<T>(
    val statusCode: HttpStatusCode,
    val payload: T? = null,
    val errorMessage: String? = null
)
