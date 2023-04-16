package ru.my_career._common.types

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class ResponseEntity<T>(
    // we dont need to duplicate the statusCode in response
    @kotlinx.serialization.Transient
    val statusCode: HttpStatusCode = HttpStatusCode.OK,
    val payload: T? = null,
    val errorMessage: String? = null
)
