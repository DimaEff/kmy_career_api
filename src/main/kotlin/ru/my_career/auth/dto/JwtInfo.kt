package ru.my_career.auth.dto

import ru.my_career._common.database.Id

data class JwtInfo(
    val userId: Id,
    val companyId: Id,
//    val permissions: Collection<String>
)
