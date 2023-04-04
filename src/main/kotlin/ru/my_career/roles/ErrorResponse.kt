package ru.my_career.roles

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val message: String) {
    companion object {
        val NOT_FOUND = ErrorResponse("permission was not found")
        val BAD_REQUEST = ErrorResponse("Invalid request")
    }
}