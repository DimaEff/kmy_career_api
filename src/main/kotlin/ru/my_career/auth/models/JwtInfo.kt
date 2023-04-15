package ru.my_career.auth.models

data class JwtInfo(
    val userId: String,
    val companyId: String? = null,
    val roles: String? = null
)
