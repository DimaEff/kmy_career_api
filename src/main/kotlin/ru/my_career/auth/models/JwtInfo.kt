package ru.my_career.auth.models

import ru.my_career.common.db.MongoId

data class JwtInfo(
    val userId: String,
    val companyId: String? = null,
    val roles: String? = null
)
